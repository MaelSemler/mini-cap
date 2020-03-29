package com.soen390.conumap.ui.directions

import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.databinding.AlternateFragmentBinding

import com.soen390.conumap.R
import com.soen390.conumap.path.Path
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.alternate_fragment.*
import java.util.*

class AlternateFragment : Fragment() {
    var prefs: SharedPreferences? = null
    val map = com.soen390.conumap.map.Map
    var startLocationAddress:String?=null

    companion object {
        fun newInstance() = AlternateFragment()
    }

    private lateinit var viewModel: AlternateViewModel
    lateinit var binding : AlternateFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: get the results from SearchCompletedFragment
        //TODO: get the currentlocation
        //TODO: add the name of the result and the current location to the start and end buttons (get them from the actual objects)

        val AlternateViewModel = ViewModelProviders.of(this)
            .get(AlternateViewModel::class.java)


        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<AlternateFragmentBinding>(inflater, R.layout.alternate_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = AlternateViewModel
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
        //TODO:Destination is hardcoded for now
        val destinationLatLng = getDestination(endLocationAddress)
        Path.setDestination(destinationLatLng)


        //TODO: Will need to be refactor, we should be calling this function from the onclick in SearchCompletedFragment
        Path.findDirections(requireActivity())

        // Alternate Routes
        binding.directionsButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_directionsFragment)
        }
        binding.startLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_directionsSearchFragment)
            editor.putString("result","1")
            editor.apply()
            editor.commit()
        }
        binding.endLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_directionsSearchFragment)
            editor.putString("result","2")
            editor.apply()
            editor.commit()
        }

        //TODO: look in if this is the best way to implement a "back" function
        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_searchCompletedFragment)
        }
        binding.transportationWalk.setOnClickListener {   //This binds the radio button to an onclick listener event that sets the transportation mode

            viewModel.setTransportation("walking")
            Path.findDirections(requireActivity())//Calls function for finding directions
            //TODO: redisplay new directions
        }
        binding.transportationBike.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            viewModel.setTransportation("bicycling")
            Path.findDirections(requireActivity()) //Calls function for finding directions
            //TODO: redisplay new directions
        }
        binding.transportationCar.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            viewModel.setTransportation("driving")
            Path.findDirections(requireActivity())//Calls function for finding directions
            //TODO: redisplay new directions
        }
        binding.transportationBus.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            viewModel.setTransportation("transit")
            Path.findDirections(requireActivity())//Calls function for finding directions
            //TODO: redisplay new directions

        }


        //TODO: enable switchOriginAndDestination button
        //TODO: implement alternative button and set the alternative here. Display the changed directions.

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
                // val transportation_mode = Path.getTransportationMode() // waiting for transportation mode available
                // Reset data in view model
                Path.resetDirections()
                // Restore direction selections
                Path.setAlternativeRoute(route_id)
                // Path.setTransportationMode(transportation_mode) // waiting for transportation mode available
                Path.findDirections(activity!!)
    //DEBUG                Toast.makeText(getActivity(),"AlternateFragment: Route changed to " + Path.getAlternatives(),Toast.LENGTH_SHORT).show()
            }

            true
        })
        return binding.root
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlternateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
