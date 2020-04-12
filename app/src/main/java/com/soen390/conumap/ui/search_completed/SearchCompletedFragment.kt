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
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import com.soen390.conumap.ui.search_results.SearchResultsViewModel
import kotlinx.android.synthetic.main.search_completed_fragment.*
import java.util.*

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

        //TODO: Change this to a two way bindings using binding.travelButton.setOnclickListener
        //directly instead of findViewByID=> Look into DirectionsFragment.kt 
        val travel_button = root.findViewById<View>(R.id.travel_button)
        val restart_button = root.findViewById<View>(R.id.restart_search)
        val location_button = root.findViewById<Button>(R.id.found_location_button)

        //Set text in location button
        location_button.text = directionsViewModel.getDestinationName()

        //This changes fragment when the "45 degree" arrow is pressed
        travel_button.setOnClickListener{
            //TODO: Set Original Location Here
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_directionsFragment)
        }
        restart_button.setOnClickListener{
            // Cancel destination in modelView
            directionsViewModel.setDestinationName("")
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchBarFragment)
        }
        location_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun getInitialOrigin(){

    }
}

