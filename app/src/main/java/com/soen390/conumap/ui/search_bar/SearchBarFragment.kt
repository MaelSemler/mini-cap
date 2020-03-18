package com.soen390.conumap.ui.search_bar

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.NavHostFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import com.soen390.conumap.R
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.search_bar_fragment.*

class SearchBarFragment : Fragment() {

    companion object {
        fun newInstance() = SearchBarFragment()
    }

    private lateinit var viewModel: SearchBarViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_bar_fragment, container, false)

        //This is the search bar
        val search_button = root.findViewById<View>(R.id.search_button) as Button
        //It is a button that calls a change of fragment
        //this method waits for the "ENTER" key to be pressed
        search_button.setOnClickListener{
            var context1 = parentFragment?.context
            //Initializes Places API using the context of the "search bar" activity
            if (context1 != null) {
                Places.initialize(context1, R.string.apiKey.toString())
            }

            //Specifies fields to return to user including place's ID, name, address and location.
            val fields: List<Place.Field> = arrayListOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
            //Creating an intent for autocomplete
            var intent = this.context?.let { it1 ->
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).setCountry("CA")
                    .build(it1)
            };
            startActivityForResult(intent, 1);
//          NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchBarViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==1)
        {
            if (resultCode==RESULT_OK)
            {
                val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
                Log.i(TAG, "Place: " + (place?.getName()) + ", " + (place?.getId()))
                if (place != null) {
                    search_button.setText(place.name)
                }
                //TODO Display the address and name of location in search bar
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR)
            {
            val status = data?.let { Autocomplete.getStatusFromIntent(it) };
                search_button.setText("Place Not Found")
                Log.i(TAG, status?.getStatusMessage());
            }
        }
    }



}
