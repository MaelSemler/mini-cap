package com.soen390.conumap.ui.search_completed
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.R
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.search_completed_fragment.*
import java.util.*

class SearchCompletedFragment : Fragment() {
    var prefs: SharedPreferences? = null

    companion object {
        fun newInstance() =
            SearchCompletedFragment()
//        val dir = directions(Map)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.search_completed_fragment, container, false)

        //TODO: Change this to a two way bindings using binding.travelButton.setOnclickListener
        //directly instead of findViewByID=> Look into DirectionsFragment.kt 
        val travel_button = root.findViewById<View>(R.id.travel_button)
        val restart_button = root.findViewById<View>(R.id.restart_search)
        val location_button = root.findViewById<Button>(R.id.found_location_button)
        prefs = requireContext().getSharedPreferences("SearchDest", 0)

        val endLocation=  prefs!!.getString("destinationLocation","" )
        location_button.setText(endLocation)

        //This changes fragment when the "45 degree" arrow is pressed
        travel_button.setOnClickListener{
            //TODO: send the result of the search (DirectionsFragment) The findDirections() function is being called directly inside of directionsFragment, but ideally we would like to keep it here
//            findDirections(activity!!)
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

        // TODO: Use the ViewModel
        //TODO: receive the result of the search result (SearchResultFragment)
        val model: SearchBarViewModel by activityViewModels()
        val destination = model.getDestination()

        //TODO: put name of the location result on the button
        found_location_button.setText(destination)
        //TODO: get the map to focus on the search result location

    }



    fun  getOrigin(startLocation: String ?): LatLng {
        val geocoderLocation : Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addressesStart: List<Address>?= geocoderLocation.getFromLocationName( startLocation, 5)
        val straddress = addressesStart!![0]

        val latitudeStart = straddress .latitude
        val longitudeStart = straddress .longitude
        val originLatLng = LatLng(latitudeStart,longitudeStart)
        return originLatLng
    }
    fun getDestination(endLocation:String?): LatLng {
        val geocoderLocation : Geocoder = Geocoder(requireContext(), Locale.getDefault())

        val addressesEnd: List<Address>?= geocoderLocation.getFromLocationName(endLocation, 5)
        val addressEnd = addressesEnd!![0]

        val latitudeEnd = addressEnd .latitude
        val longitudeEnd = addressEnd .longitude

        val destinationLatLng = LatLng(latitudeEnd, longitudeEnd)
        return destinationLatLng
    }


}

