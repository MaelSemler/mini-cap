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
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.search_results_fragment.*


class SearchResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_results_fragment, container, false)

        //TODO: Recently visited
        //TODO: DO RESEARCH ON SEARCHVIEWS and if we have to change this entire thing!!!!!!!!!!!

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
        //    TODO:Programmatically Enforce Autocomplete

//        val token = AutocompleteSessionToken.newInstance() //initialize session token
//        while (searchBar.setOnKeyListener() //Create a while loop here to constantly take user input and display accordingly to what's typed
//        {
//            val searchInput = searchBar.getText().toString()
//            val request = //Requesting predictions with these specific parameters
//                FindAutocompletePredictionsRequest.builder()
//                    .setLocationBias(bounds)
//                    .setTypeFilter(TypeFilter.ADDRESS)
//                    .setCountries("CA")
//                    .setSessionToken(token)
//                    .setQuery(searchInput)
//                    .build()
//            Toast.makeText(this.context, searchBar.getText().toString(), Toast.LENGTH_SHORT).show();
//            placesClient?.findAutocompletePredictions(request)
//                ?.addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
//                    for (prediction in response.autocompletePredictions) {
//                        result.append(prediction.getFullText(null)).append("\n")
//                        Log.i(SearchResultsFragment().getTag(), prediction.placeId)
//                        Log.i(
//                            SearchResultsFragment().getTag(),
//                            prediction.getPrimaryText(null).toString()
//                        )
//                        Toast.makeText(
//                            this.context,
//                            prediction.getPrimaryText(null),
//                            Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                    searchResults.text = result
//                }?.addOnFailureListener { exception: Exception? ->
//                    if (exception is ApiException) {
//                        Log.e(
//                            SearchResultsFragment().getTag(),
//                            "Place not found: " + exception.statusCode
//                        )
//                    }
 //         }
        // }

        /* This is the search bar edit text. This method waits for the "ENTER" key to be pressed
        It changes fragment when it is pressed*/
        // This button clears the edit text input when it is pressed
        clearButton.setOnClickListener {
            searchBar.setText("")
        }

        cancelButton.setOnClickListener {
            // Cancel destination in modelView
            val model: SearchBarViewModel by activityViewModels()
            model.setDestination("")
            //TODO: look in if this is the best way to implement a "back" function
            NavHostFragment.findNavController(this).navigateUp()
            //TODO:if keyboard is shown, hide the keyboard
            //hideKeyboard()
        }
        return root
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //Shows the keyboard
//        //TODO:if keyboard is hidden, show the keyboard
//        showKeyboard()
//
//    }

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
    //TODO: issue when the app closes the keyboard doesn't
    // TODO: Understand what this does.

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

