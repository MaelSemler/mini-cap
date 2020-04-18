package com.soen390.conumap.ui.directions

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.model.LatLng
import androidx.navigation.fragment.NavHostFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.map.Map
import com.soen390.conumap.path.Path
import kotlinx.android.synthetic.main.directions_fragment.*
import kotlinx.android.synthetic.main.search_results_fragment.*
import java.util.*


class DirectionsFragment : Fragment() {

    companion object {
        fun newInstance() = DirectionsFragment()
    }
    lateinit var binding : DirectionsFragmentBinding
    val directionsViewModel: DirectionsViewModel by activityViewModels()
    var path= Path
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.directions_fragment, container, false)

        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel=path
        }


        //Initial transportation mode is already driving
        if (directionsViewModel.destinationChanged.value==null)
        {
            //If you're using an emulator, use the first commented out line
            //If you're using a phone, use the second commented out line
            //PLEASE TEST THIS
            directionsViewModel.originLocation.value= LatLng(45.7,-73.3)
            //directionsViewModel.originLocation.value= Map.getCurrentLocation()
            Path.setOrigin(directionsViewModel.originLocation.value!!)
        }
        if (directionsViewModel.originName.value==null)
            directionsViewModel.originName.value="Current Location"

        binding.startLocationButton.setText(directionsViewModel.originName.value)
        binding.endLocationButton.setText(directionsViewModel.destinationName.value)


        Path.findDirections(requireActivity())

        //Select radio button corresponding to transportation mode active
        when(Path.getTransportation())
        {
            getString(R.string.driving) -> binding.transportationCar.setChecked(true)
            getString(R.string.bicycling) -> binding.transportationBike.setChecked(true)
            getString(R.string.walking) -> binding.transportationWalk.setChecked(true)
            getString(R.string.transit) -> binding.transportationBus.setChecked(true)
        }

        // Alternate Routes
        binding.altButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_alternateFragment)
        }
        binding.startLocationButton.setOnClickListener{
            directionsViewModel.destinationChanged.value=false
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_searchResultsFragment)
             Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.endLocationButton.setOnClickListener{
            directionsViewModel.destinationChanged.value=true
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_searchResultsFragment)
            Path.findDirections(requireActivity())//Calls function for finding directions
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
            switchButtons()
            binding.startLocationButton.text= directionsViewModel.originName!!.value
            binding.endLocationButton.text= directionsViewModel.destinationName!!.value
            Path.findDirections(requireActivity())
        }
        //TODO: implement alternative button and set the alternative here. Display the changed directions.
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        end_location_button.setText(directionsViewModel.destinationName.value)

    }



    fun switchButtons(){
        var originLocation=directionsViewModel.originLocation!!.value
        directionsViewModel.originLocation!!.value=directionsViewModel.destinationLocation!!.value
        directionsViewModel.destinationLocation!!.value=originLocation
        Path.setOrigin(directionsViewModel.originLocation.value!!)
        Path.setDestination(directionsViewModel.destinationLocation.value!!)

        var originName=directionsViewModel.originName!!.value
        directionsViewModel.originName!!.value=directionsViewModel.destinationName!!.value
        directionsViewModel.destinationName!!.value=originName

        var originAddress=directionsViewModel.originAddress!!.value
        directionsViewModel.originAddress!!.value=directionsViewModel.destinationAddress!!.value
        directionsViewModel.destinationAddress!!.value=originAddress
    }

}
