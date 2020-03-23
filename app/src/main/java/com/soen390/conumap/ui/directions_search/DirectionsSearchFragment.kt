package com.soen390.conumap.ui.directions_search

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import com.soen390.conumap.R
import kotlinx.android.synthetic.main.search_results_fragment.*

class DirectionsSearchFragment : Fragment() {
    var prefs: SharedPreferences? = null
    lateinit var search_bar : Button;
    companion object {
        fun newInstance() = DirectionsSearchFragment()
    }

    private lateinit var viewModel: DirectionsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.directions_search_fragment, container, false)

        //TODO: check if this class is necessary or if we could just use SearchResultsFragment
        //TODO: the search suggestions, recently visited
        //TODO: DO RESEARCH ON SEARCHVIEWS and if we have to change this entire thing!!!!!!!!!!!

        val return_button = root.findViewById<View>(R.id.return_direction) as Button
        val clear_button = root.findViewById<View>(R.id.clear_search) as Button
        search_bar = root.findViewById<View>(R.id.DirectionSearchResults) as Button

        //     search_bar = root.findViewById<View>(R.id.DirectionSearchResults) as Button

        //Selects the search bar input
        this.context?.let {
            Places.initialize(it, context?.getString(R.string.apiKey)!!)
        };  //initialize context
        val placesClient = this.context?.let { Places.createClient(it) }   //initialize placesClient
        val autoCompleteRequestCode= 1
        val bounds = RectangularBounds.newInstance(
            LatLng(45.425579, -73.687204),
            LatLng(45.706574, -73.475121)
        )
        /*search_bar.requestFocus()
        //This is the search bar edit text
        //this method waits for the "ENTER" key to be pressed
        //It changes fragment when it is pressed
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //TODO: send the results back ti DirectionsFragment
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_directionsSearchFragment_to_directionsFragment)
            }
            false
        })*/

        search_bar.setOnClickListener {
            val fields = arrayListOf (Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
            val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields).setCountry("CA").setLocationBias(bounds)
                .build(this.requireContext());
            startActivityForResult(intent, autoCompleteRequestCode);
        }

        //This button clears the edit text input when it is pressed
        clear_button.setOnClickListener {
            search_bar.setText("")
        }

        //This button goes to the previous fragment
        //TODO: look in if this is the best way to implement a "back" function
        return_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
            //TODO:if keyboard is shown hide the keyboard
            hideKeyboard()

        }
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Shows the keyboard
        showKeyboard()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Displays the appropriate thing according to what the user selected.
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK)
        {
            val place= data?.let { Autocomplete.getPlaceFromIntent(it) }
            Log.i(ContentValues.TAG,"Place: "+ place!!.name+ ","+place.id)
            search_bar.setText(place.name)

            prefs = requireContext().getSharedPreferences("SearchCount", 0)
            val count=  prefs!!.getString("result","" )
            Log.e("count",count)

            if ( count.equals("1")){
                val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchCurrent",0)

                val editor:SharedPreferences.Editor =  sharedPreferences.edit()


                editor.putString("currentLocation",search_bar.text.toString())

                editor.apply()
                editor.commit()}
            else {
                val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchDest",0)

                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("destinationLocation",search_bar.text.toString())

                editor.apply()
                editor.commit()
            }
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_directionsSearchFragment_to_directionsFragment)
        }
        else if (resultCode== AutocompleteActivity.RESULT_ERROR)
        {
            val status=Autocomplete.getStatusFromIntent(data!!)
            Log.i(ContentValues.TAG, status.statusMessage)
            search_bar.setText("Error")
        }
    }

    //TODO:simply the logic!!!
    //This function is to make the keyboard close
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }

    //TODO:simply the logic!!!
    //This function is to make the keyboard open
    fun Fragment.showKeyboard() {
        view?.let { activity?.showKeyboard(it) }
    }

    fun Activity.showKeyboard() {
        showKeyboard(currentFocus ?: View(this))
    }

    fun Context.showKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }
}
