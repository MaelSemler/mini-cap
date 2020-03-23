package com.soen390.conumap.ui.search_completed

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.GoogleMap
import com.soen390.conumap.Directions.directions
import com.soen390.conumap.Directions.directions.routeTest

import com.soen390.conumap.R
import com.soen390.conumap.map.Map
//import com.soen390.conumap.map.Map.getMap

class SearchCompletedFragment : Fragment() {
    var prefs: SharedPreferences? = null

    companion object {
        fun newInstance() =
            SearchCompletedFragment()
//        val dir = directions(Map)
    }

    private lateinit var viewModel: SearchCompletedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_completed_fragment, container, false)

        //TODO: receive the result of the search result (SearchResultFragment)
        //TODO: put name of the location result on the button
        //TODO: get the map to focus on the search result location

        //TODO: Change this to a two way bindings using binding.travelButton.setOnclickListener 
        //directly instead of findViewByID=> Look into DirectionsFragment.kt 
        val travel_button = root.findViewById<View>(R.id.travel_button)
        val restart_button = root.findViewById<View>(R.id.restart_search)
        val location_button = root.findViewById<Button>(R.id.found_location_button)
        prefs = requireContext().getSharedPreferences("SearchDest", 0)
        val endLocation = prefs!!.getString("destinationLocation","" )
        location_button.setText(endLocation)
        //This changes fragment when the "45 degree" arrow is pressed
        travel_button.setOnClickListener{
            //TODO: send the result of the search (DirectionsFragment)
            //routeTest(activity!!)
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_directionsFragment)
        }
        restart_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchBarFragment)
        }
        location_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchCompletedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

