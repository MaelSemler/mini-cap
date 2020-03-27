package com.soen390.conumap.ui.search_completed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
//import com.soen390.conumap.Directions.Directions.routeTest

import com.soen390.conumap.R
import com.soen390.conumap.path.Path.findDirections
import com.soen390.conumap.path.PathViewModel
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.search_completed_fragment.*

//import com.soen390.conumap.map.Map.getMap

class SearchCompletedFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchCompletedFragment()
//        val dir = directions(Map)
    }
    val pathviewModel: PathViewModel by activityViewModels()
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.search_completed_fragment, container, false)

        //TODO: Change this to a two way bindings using binding.travelButton.setOnclickListener
        //directly instead of findViewByID=> Look into DirectionsFragment.kt 
        val travel_button = root.findViewById<View>(R.id.travel_button)
        val restart_button = root.findViewById<View>(R.id.restart_search)
        val location_button = root.findViewById<View>(R.id.found_location_button)

        //This changes fragment when the "45 degree" arrow is pressed
        travel_button.setOnClickListener{
            pathviewModel.transportationMode.value = "walking"
            findDirections(activity!!)
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_directionsFragment)
        }
        restart_button.setOnClickListener{
            // Cancel destination in modelView
            val model: SearchBarViewModel by activityViewModels()
            model.setDestination("")
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchBarFragment)
        }
        location_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
        //TODO: receive the result of the search result (SearchResultFragment)
        val model: SearchBarViewModel by activityViewModels()
        val destination = model.getDestination()

        //TODO: put name of the location result on the button
        found_location_button.setText(destination)
        //TODO: get the map to focus on the search result location

    }

}

