package com.soen390.conumap.ui.search_completed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
//import com.soen390.conumap.Directions.Directions.routeTest

import com.soen390.conumap.R
import com.soen390.conumap.path.path.findShortestDirections

//import com.soen390.conumap.map.Map.getMap

class SearchCompletedFragment : Fragment() {

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

//        //TODO: send the result of the search
//        val go_button = root.findViewById<View>(R.id.go_button)
//        go_button.setOnClickListener{
//            dir.routeTest(activity!!)
//        }

        //TODO: receive the result of the search result (SearchResultFragment)
        //TODO: put name of the location result on the button
        //TODO: get the map to focus on the search result location


        val travel_button = root.findViewById<View>(R.id.travel_button)
        val restart_button = root.findViewById<View>(R.id.restart_search)
        val location_button = root.findViewById<View>(R.id.found_location_button)

        //This changes fragment when the "45 degree" arrow is pressed
        travel_button.setOnClickListener{
            //TODO: send the result of the search (DirectionsFragment)
            findShortestDirections(activity!!)
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

