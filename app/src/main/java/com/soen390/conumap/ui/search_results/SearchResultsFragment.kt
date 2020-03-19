package com.soen390.conumap.ui.search_results

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.soen390.conumap.R


class SearchResultsFragment : Fragment() {

    private lateinit var viewModel: SearchResultsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_results_fragment, container, false)

        //TODO: Recently visited
        //TODO: DO RESEARCH ON SEARCHVIEWS and if we have to change this entire thing!!!!!!!!!!!

        //Getting the Views from the fragment
        val cancel_button = root.findViewById<View>(R.id.cancel_search) as Button
        val clear_button = root.findViewById<View>(R.id.clear_input) as Button
        val search_bar = root.findViewById<View>(R.id.search_button) as Button
        val search_bar_text = root.findViewById<View>(R.id.search_button_text) as EditText
        val search_results = root.findViewById<View>(R.id.SearchResults)


        search_bar.setOnClickListener {
            this.context?.let {
                Places.initialize(
                    it,
                    R.string.apiKey.toString()
                )
            };  //initialize context
            var placesClient =
                this.context?.let { Places.createClient(it) };   //initialize placesClient
            val token = AutocompleteSessionToken.newInstance() //initalize session token
            val bounds = RectangularBounds.newInstance(
                LatLng(45.425579, -73.687204),
                LatLng(45.706574, -73.475121)
            )
            val request = //Requesting predictions with these specific parameters
                FindAutocompletePredictionsRequest.builder()
                    .setLocationBias(bounds)
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setCountries("CA")
                    .setSessionToken(token)
                    .setQuery(search_bar_text.getText().toString())
                    .build()
            placesClient!!.findAutocompletePredictions(request)
                .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                    for (prediction in response.autocompletePredictions) {
                        Log.i(TAG, prediction.placeId)
                        Log.i(TAG, prediction.getPrimaryText(null).toString())
                    }
                }.addOnFailureListener { exception: Exception? ->
                    if (exception is ApiException) {
                        Log.e(TAG, "Place not found: " + exception.statusCode)
                    }
                }
        }

        search_bar.requestFocus()                     //Selects the search bar input. It's like if the mouse had already clicked on the editbox


        /* This is the search bar edit text. This method waits for the "ENTER" key to be pressed
        It changes fragment when it is pressed*/
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //TODO: send the result (SearchCompletedFragment)
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
            }
            false
        })

        // This button clears the edit text input when it is pressed
        clear_button.setOnClickListener {
            search_bar.setText("")
        }

        cancel_button.setOnClickListener {
            //TODO: look in if this is the best way to implement a "back" function
            NavHostFragment.findNavController(this).navigateUp()
            //TODO:if keyboard is shown, hide the keyboard
            //hideKeyboard()

        }
        return root
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchResultsViewModel::class.java)
        // TODO: Use the ViewModel

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Shows the keyboard
        //TODO:if keyboard is hidden, show the keyboard
        showKeyboard()

    }

    //TODO:simply the logic of hideKeyboard() and showKeyboard
    //TODO: issue when the app closes the keyboard doesn't

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

