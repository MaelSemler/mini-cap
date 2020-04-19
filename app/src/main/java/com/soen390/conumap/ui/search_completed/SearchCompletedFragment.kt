package com.soen390.conumap.ui.search_completed
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.ui.directions.DirectionsViewModel

class SearchCompletedFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchCompletedFragment()
    }
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.search_completed_fragment, container, false)
        val directionsViewModel: DirectionsViewModel by activityViewModels()

        //directly instead of findViewByID=> Look into DirectionsFragment.kt 
        val travelButton = root.findViewById<View>(R.id.travel_button)
        val restartButton = root.findViewById<View>(R.id.restart_search)
        val locationButton = root.findViewById<Button>(R.id.found_location_button)

        //Set text in location button
        locationButton.text = directionsViewModel.destinationName.value

        //This changes fragment when the "45 degree" arrow is pressed
        travelButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_directionsFragment)
        }
        restartButton.setOnClickListener{ //directionsViewModel.getDestinationName().postValue("")
            val model: DirectionsViewModel by activityViewModels()
            model.destinationName.value = null
            model.destinationLocation.value = null
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchBarFragment)
        }
        locationButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

