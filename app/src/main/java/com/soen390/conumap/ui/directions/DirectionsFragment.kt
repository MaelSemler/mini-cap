package com.soen390.conumap.ui.directions

import android.content.SharedPreferences
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.path.Path
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.directions_fragment.*
import java.util.*


class DirectionsFragment : Fragment() {
    var prefs: SharedPreferences? = null
    val map = com.soen390.conumap.map.Map
    var startLocationAddress:String?=null

    companion object {
        fun newInstance() = DirectionsFragment()
    }

    private lateinit var viewModel: DirectionsViewModel
    lateinit var binding : DirectionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.directions_fragment, container, false)
        val directionViewModel = ViewModelProviders.of(this)
            .get(DirectionsViewModel::class.java)


        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = directionViewModel
        }

        //To change starting and ending location, just click on the buttons to be sent to a search fragment

        prefs = requireContext().getSharedPreferences("SearchCurrent", 0)
        val startLocation=  prefs!!.getString("currentLocation","" )
        startLocationAddress=prefs!!.getString("currentLocationAddress","" )
        binding.startLocationButton.setText(startLocation)
        prefs = requireContext().getSharedPreferences("SearchDest", 0)
        val endLocation=  prefs!!.getString("destinationLocation","" )
        val endLocationAddress=  prefs!!.getString("destinationLocationAddress","" )

        binding.endLocationButton.setText(endLocation)
        if(startLocation.equals("")){
            val originLatLng = map.getCurrentLocation()
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(requireContext(), Locale.getDefault())

            addresses = geocoder.getFromLocation(
                originLatLng.latitude,
                originLatLng.longitude,
                1)


            val address = addresses[0].getAddressLine(0)
            val city = addresses[0].getLocality()

            binding.startLocationButton.setText(address)
            startLocationAddress=address

        }

        val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchCount",0)

        val editor: SharedPreferences.Editor =  sharedPreferences.edit()


        map?.getMapInstance().clear()
        val originLatLng = getOrigin(startLocationAddress)
        Path.setOrigin(originLatLng)
        val destinationLatLng = getDestination(endLocationAddress)
        Path.setDestination(destinationLatLng)

        // Select radio button corresponding to transportation mode active
        when(Path.getTransportation()){
            getString(R.string.driving) -> binding.transportationCar.setChecked(true)
            getString(R.string.bicycling) -> binding.transportationBike.setChecked(true)
            getString(R.string.walking) -> binding.transportationWalk.setChecked(true)
            getString(R.string.transit) -> binding.transportationBus.setChecked(true)
        }

        //TODO: Will need to be refactor, we should be calling this function from the onclick in SearchCompletedFragment
        Path.findDirections(requireActivity())

        // Alternate Routes
        binding.altButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_alternateFragment)
        }
        binding.startLocationButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_directionsSearchFragment)
            editor.putString("result","1")
            editor.apply()
            editor.commit()
        }
        binding.endLocationButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_directionsSearchFragment)
            editor.putString("result","2")
            editor.apply()
            editor.commit()
        }

        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.searchCompletedFragment)
        }
        binding.transportationWalk.setOnClickListener {   //This binds the radio button to an onclick listener event that sets the transportation mode

            Path.setTransportation(getString(R.string.walking))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.transportationBike.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            Path.setTransportation(getString(R.string.bicycling))
            Path.findDirections(requireActivity()) //Calls function for finding directions
        }
        binding.transportationCar.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            Path.setTransportation(getString(R.string.driving))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.transportationBus.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            Path.setTransportation(getString(R.string.transit))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        //TODO: enable switchOriginAndDestination button
        return binding.root
    }

    fun  getOrigin(startLocation: String ?):LatLng{
        val geocoderLocation : Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addressesStart: List<Address>?= geocoderLocation.getFromLocationName( startLocation, 5)
        val straddress = addressesStart!![0]

        val latitudeStart = straddress .latitude
        val longitudeStart = straddress .longitude
        val originLatLng = LatLng(latitudeStart,longitudeStart)
        return originLatLng
    }
    fun getDestination(endLocation:String?):LatLng{
        val geocoderLocation : Geocoder = Geocoder(requireContext(), Locale.getDefault())

        val addressesEnd: List<Address>?= geocoderLocation.getFromLocationName(endLocation, 5)
        val addressEnd = addressesEnd!![0]

        val latitudeEnd = addressEnd .latitude
        val longitudeEnd = addressEnd .longitude

        val destinationLatLng = LatLng(latitudeEnd, longitudeEnd)
        return destinationLatLng
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)
        val model: SearchBarViewModel by activityViewModels()
        val destination = model.getDestination()
        end_location_button.setText(destination)

    }





}
