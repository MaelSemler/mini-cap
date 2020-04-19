package com.soen390.conumap.ui.search_results

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.soen390.conumap.R
import com.soen390.conumap.path.Path
import com.soen390.conumap.ui.directions.DirectionsViewModel
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.search_results_fragment.*


class SearchResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.search_results_fragment, container, false)
        val model= ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

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
           val fields = arrayListOf (Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
           val intent = Autocomplete.IntentBuilder(
                 AutocompleteActivityMode.OVERLAY, fields).setCountry("CA").setLocationBias(bounds)
                .build(this.requireContext());
         startActivityForResult(intent, autoCompleteRequestCode);


        // This button clears the edit text input when it is pressed
        clearButton.setOnClickListener {
            searchBar.setText("")
        }

        // Cancel destination in modelView
        cancelButton.setOnClickListener {
            model.destinationName.value=""
            model.destinationAddress.value=""
            model.destinationLocation.value=LatLng(45.494,-73.577)
            NavHostFragment.findNavController(this).navigateUp()
        }
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Displays the appropriate thing according to what the user selected.
        val model: DirectionsViewModel by activityViewModels()
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==RESULT_OK)
        {
            val place= data?.let { Autocomplete.getPlaceFromIntent(it) }
            Log.i(TAG,"Place: "+ place!!.name+ ","+place.id)
            if (model.destinationChanged.value==true||model.destinationChanged.value==null)
            {
                model.destinationName.value= place.name
                model.destinationAddress.value= place.address
                model.destinationLocation.value= place.latLng
                Path.setDestination(model.destinationLocation.value!!)
            }
            if (model.destinationChanged.value==false)
            {
                model.originName.value= place.name
                model.originAddress.value=place.address
                model.originLocation.value=place.latLng
                Path.setOrigin(model.originLocation.value!!)
            }
        }
        else if (resultCode==AutocompleteActivity.RESULT_ERROR)
        {
            val status=Autocomplete.getStatusFromIntent(data!!)
            Log.i(TAG, status.statusMessage.toString())
            searchBar.setText("Error")
            NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
        }
        if (model.destinationChanged.value==null)
            NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
        else if (model.destinationChanged.value==true||model.destinationChanged.value==false)
            NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_directionsFragment)
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

