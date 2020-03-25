package com.soen390.conumap.ui.directions

import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
=======
import android.widget.RadioButton
>>>>>>> 4fbded3ae2805e2125ad949f138b953132b8107c
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.directions

import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import java.util.*

class DirectionsFragment : Fragment() {
    var prefs: SharedPreferences? = null
    val map = com.soen390.conumap.map.Map
    companion object {
        fun newInstance() = DirectionsFragment()
    }

    private lateinit var viewModel: DirectionsViewModel
    lateinit var binding : DirectionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.directions_fragment, container, false)
        //TODO: get the results from SearchCompletedFragment
        //TODO: get the currentlocation
        //TODO: add the name of the result and the curent location to the start and end buttons (get them from the actual objects)

        val directionViewModel = ViewModelProviders.of(this)
            .get(DirectionsViewModel::class.java)

        val walkButton = root.findViewById<View>(R.id.transportation_walk) as RadioButton
        val busButton = root.findViewById<View>(R.id.transportation_bus) as RadioButton
        val carButton = root.findViewById<View>(R.id.transportation_car) as RadioButton
        val bikeButton= root.findViewById<View>(R.id.transportation_bike) as RadioButton

        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = directionViewModel
        }

        //To change starting and ending location, just click on the buttons to be sent to a search fragment
        prefs = requireContext().getSharedPreferences("SearchCount", 0)
        val count=  prefs!!.getString("result","" )
        prefs = requireContext().getSharedPreferences("SearchCurrent", 0)
        val startLocation=  prefs!!.getString("currentLocation","" )
        binding.startLocationButton.setText(startLocation)
        prefs = requireContext().getSharedPreferences("SearchDest", 0)
        val endLocation = prefs!!.getString("destinationLocation","" )
        binding.endLocationButton.setText(endLocation)
        if(startLocation.equals("")){
            val originLatLng = map.getCurrentLocation()
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(requireContext(), Locale.getDefault())

            addresses = geocoder.getFromLocation(
                originLatLng.latitude,
                originLatLng.longitude,
                1
            )

            val address =
                addresses[0].getAddressLine(0)
            val city = addresses[0].getLocality()

            binding.startLocationButton.setText(address)

        }
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("SearchCount",0)

        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        val geocoderLocation :Geocoder= Geocoder(requireContext(), Locale.getDefault())
        val addressesStart: List<Address>?= geocoderLocation.getFromLocationName( binding.startLocationButton.text.toString(), 5)
        val straddress = addressesStart!![0]
        map?.getMapInstance().clear()
        val latitudeStart = straddress .latitude
        val longitudeStart = straddress .longitude
        val addressesEnd: List<Address>?= geocoderLocation.getFromLocationName( binding.endLocationButton.text.toString(), 5)
        val addressEnd = addressesEnd!![0]

        val latitudeEnd = addressEnd .latitude
        val longitudeEnd = addressEnd .longitude
        val originLatLng = LatLng(latitudeStart, longitudeStart)

        val destinationLatLng = LatLng(latitudeEnd, longitudeEnd)

        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)


        directions.route(requireActivity(), originLatLng, destinationLatLng)


        binding.startLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(com.soen390.conumap.R.id.action_directionsFragment_to_directionsSearchFragment)
            editor.putString("result","1")
            editor.apply()
            editor.commit()

        }
        binding.endLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_directionsSearchFragment)
            editor.putString("result","2")
            editor.apply()
            editor.commit()

        }
        //TODO: look in if this is the best way to implement a "back" function
        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_searchCompletedFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

    }


}
