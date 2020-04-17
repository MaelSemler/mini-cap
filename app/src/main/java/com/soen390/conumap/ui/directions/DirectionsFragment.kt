package com.soen390.conumap.ui.directions

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
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
    var  startLocationText:String?=null
    var endLocationText:String?=null
    var startLocation:String?=null
    var endLocation:String?=null
    var endLocationAddress:String?=null

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

        getPath()

        val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchCount",0)

        val editor: SharedPreferences.Editor =  sharedPreferences.edit()


        map?.getMapInstance().clear()
        val originLatLng = getOrigin(startLocationAddress)
        Path.setOrigin(originLatLng)
        val destinationLatLng = getDestination(endLocationAddress)
        Path.setDestination(destinationLatLng)

        // Paint Current Button
        binding.run {
            Path.setDestination(destinationLatLng)

            if(Path.getDirectionScreenMode()!!) {
                // Paint Current Button
                directionsButton.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.colorAccent)))
                directionsButton.setTextColor(resources.getColor(R.color.buttonColor))
                altButton.setTextColor(resources.getColor(R.color.colorAccent))
            }else{
                // Paint Current Button
                altButton.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.colorAccent)))
                altButton.setTextColor(resources.getColor(R.color.buttonColor))
                directionsButton.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
        // Select radio button corresponding to transportation mode active
        when(Path.getTransportation()){
            getString(R.string.driving) -> binding.transportationCar.setChecked(true)
            getString(R.string.bicycling) -> binding.transportationBike.setChecked(true)
            getString(R.string.walking) -> binding.transportationWalk.setChecked(true)
            getString(R.string.transit) -> binding.transportationBus.setChecked(true)
        }

        //TODO: Will need to be refactor, we should be calling this function from the onclick in SearchCompletedFragment
        // FOR DEV26-switching
        Path.findDirections(requireActivity())

        // Alternate Routes
        binding.altButton.setOnClickListener{
            Path.setDirectionsScreenMode("alt")
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_self)
        }
        // Direction Routes
        binding.directionsButton.setOnClickListener{
            Path.setDirectionsScreenMode("dir")
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_self)
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
        //enable switchOriginAndDestination button
        binding.switchButton.setOnClickListener{

            val sharedPreferencesstartLocation: SharedPreferences =requireContext().getSharedPreferences("SearchCurrent",0)
            val editorstart:SharedPreferences.Editor =  sharedPreferencesstartLocation.edit()
            editorstart.putString("currentLocation",endLocation)
            editorstart.putString("currentLocationAddress",endLocationAddress)
            editorstart.apply()
            editorstart.commit()

            val sharedPreferences: SharedPreferences =requireContext().getSharedPreferences("SearchDest",0)

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("destinationLocation",startLocation)
            editor.putString("destinationLocationAddress",startLocationAddress)
            // Log.e("nfe",place.name);
            editor.apply()
            editor.commit()
            getPath()
        }

        //TODO: implement alternative button and set the alternative here. Display the changed directions.


        if(!Path.getDirectionScreenMode()!!) {
            binding.DirectionsTextBox.setOnTouchListener(View.OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // Toast.makeText(getActivity(), "AlternateFragment: Touch coordinates : " +  event.x.toString() + " x " + event.y.toString(), Toast.LENGTH_SHORT).show()
                    // Change route
                    if (event.y < 300) {
                        //First line selected
                        if (Path.getAlternatives() == 0) {
                            Path.setAlternativeRoute(1)
                        } else {
                            Path.setAlternativeRoute(0)
                        }
                    } else {
                        //Second Line selected
                        if (Path.getAlternatives() == 2) {
                            Path.setAlternativeRoute(1)
                        } else {
                            Path.setAlternativeRoute(2)
                        }
                    }
                    // Save Context
                    val route_id = Path.getAlternatives()
                    Path.resetDirections()
                    Path.setAlternativeRoute(route_id)
                    Path.findDirections(requireActivity())
                }

                true
            })
        }

        return binding.root
    }

    fun getPath(){

        prefs = requireContext().getSharedPreferences("SearchCurrent", 0)
        startLocation=  prefs!!.getString("currentLocation","" )
        startLocationAddress=prefs!!.getString("currentLocationAddress","" )
        binding.startLocationButton.setText(startLocation)

        prefs = requireContext().getSharedPreferences("SearchDest", 0)
        endLocation=  prefs!!.getString("destinationLocation","" )
        endLocationAddress=  prefs!!.getString("destinationLocationAddress","" )

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

        map?.getMapInstance().clear()
        val originLatLng = getOrigin(startLocationAddress)
        Path.setOrigin(originLatLng)
        val destinationLatLng = getDestination(endLocationAddress)
        Path.setDestination(destinationLatLng)
        Path.setTransportation(getString(R.string.driving))

        Path.findDirections(requireActivity())

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
        //val destination = model.getDestination()
        //end_location_button.setText(destination)

    }
}
