package com.soen390.conumap.ui.search_results

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.soen390.conumap.R
import com.soen390.conumap.path.Path
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.search_results_fragment.*


class SearchResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_results_fragment, container, false)

        //TODO: Recently visited

        //Getting the Views from the fragment
        val cancelButton = root.findViewById<View>(R.id.cancel_search) as Button
        val clearButton = root.findViewById<View>(R.id.clear_input) as Button
        val searchBar = root.findViewById<View>(R.id.searchBar) as Button

        //Autocomplete using Intent
        this.context?.let {
            Places.initialize(it, context?.getString(R.string.apiKey)!!)
        };  //initialize context
        val placesClient = this.context?.let { Places.createClient(it) }   //initialize placesClient
        val autoCompleteRequestCode= 1
        val bounds = RectangularBounds.newInstance(
            LatLng(45.425579, -73.687204),
            LatLng(45.706574, -73.475121))
           val fields = arrayListOf (Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
           val intent = Autocomplete.IntentBuilder(
                 AutocompleteActivityMode.OVERLAY, fields).setCountry("CA").setLocationBias(bounds)
                .build(this.requireContext());
         startActivityForResult(intent, autoCompleteRequestCode);
        /* TODO Enforce Autocomplete Programmatically */
        Path.setTransportation((getString(R.string.driving)))

        // This button clears the edit text input when it is pressed
        clearButton.setOnClickListener {
            searchBar.setText("")
        }

        cancelButton.setOnClickListener {
            // Cancel destination in modelView
            val model: SearchBarViewModel by activityViewModels()
            model.setDestination("")
            NavHostFragment.findNavController(this).navigateUp()
            //TODO:if keyboard is shown, hide the keyboard
        }
        return root
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Displays the appropriate thing according to what the user selected.
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==RESULT_OK)
        {
            val place= data?.let { Autocomplete.getPlaceFromIntent(it) }
            Log.i(TAG,"Place: "+ place!!.name+ ","+place.id)
            searchBar.setText(place.name)
            val model: SearchBarViewModel by activityViewModels()
            model.setDestination(place.name)
            model.setDestinationAddress(place.address)
            val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchDest",0)

            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("destinationLocation",place.name)
            editor.putString("destinationLocationAddress",place.address)
            editor.apply()
            editor.commit()
        }
        else if (resultCode==AutocompleteActivity.RESULT_ERROR)
        {
            val status=Autocomplete.getStatusFromIntent(data!!)
            Log.i(TAG, status.statusMessage)
            searchBar.setText("Error")
        }
        NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
    }


    //TODO:simply the logic of hideKeyboard() and showKeyboard

    //This function is to make the keyboard close
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }
    fun Activity.hideKeyboard() {
      hideKeyboard(currentFocus ?: View(this))
    }



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

