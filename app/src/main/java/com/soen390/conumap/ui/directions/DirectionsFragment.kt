package com.soen390.conumap.ui.directions

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.path.Path
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import com.soen390.conumap.ui.search_results.SearchResultsViewModel
import kotlinx.android.synthetic.main.directions_fragment.*
import kotlinx.android.synthetic.main.search_results_fragment.*
import java.util.*


class DirectionsFragment : Fragment() {

    companion object {
        fun newInstance() = DirectionsFragment()
    }

    private lateinit var directionsViewModel: DirectionsViewModel
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
        //Initial transportation mode is already driving
        //Path.findDirections(requireActivity())

        // Select radio button corresponding to transportation mode active
        when(directionsViewModel.getTransportation()){
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
            initializeAutocomplete()
            binding.endLocationButton.text
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.endLocationButton.setOnClickListener{
            initializeAutocomplete()
            Path.findDirections(requireActivity())//Calls function for finding directions
        }

        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.searchCompletedFragment)
        }
        binding.transportationWalk.setOnClickListener {   //This binds the radio button to an onclick listener event that sets the transportation mode

            directionViewModel.setTransportation(getString(R.string.walking))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.transportationBike.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            directionViewModel.setTransportation(getString(R.string.bicycling))
            Path.findDirections(requireActivity()) //Calls function for finding directions
        }
        binding.transportationCar.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            directionViewModel.setTransportation(getString(R.string.driving))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }
        binding.transportationBus.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
            directionViewModel.setTransportation(getString(R.string.transit))
            Path.findDirections(requireActivity())//Calls function for finding directions
        }

        //enable switchOriginAndDestination button
        binding.switchButton.setOnClickListener{
            switchButtons()
            binding.startLocationButton.text=directionsViewModel.getOriginName()
            binding.endLocationButton.text=directionsViewModel.getDestinationName()
            Path.findDirections(requireActivity())
        }
        //TODO: implement alternative button and set the alternative here. Display the changed directions.
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        directionsViewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

    }


    fun initializeAutocomplete(){
        this.context?.let {
            Places.initialize(it, context?.getString(R.string.apiKey)!!)
        };  //initialize context
        val placesClient = this.context?.let { Places.createClient(it) }   //initialize placesClient
        val autoCompleteRequestCode= 1
        val bounds = RectangularBounds.newInstance(
            LatLng(45.425579, -73.687204),
            LatLng(45.706574, -73.475121))
        val fields = arrayListOf (Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields).setCountry("CA").setLocationBias(bounds)
            .build(this.requireContext());
        startActivityForResult(intent, autoCompleteRequestCode)

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Place? { //Displays the appropriate thing according to what the user selected.
            super.onActivityResult(requestCode, resultCode, data)
            val model: DirectionsViewModel by activityViewModels()
            if (resultCode== Activity.RESULT_OK)
            {
                val place= data?.let { Autocomplete.getPlaceFromIntent(it) }
                return place
            }
            else if (resultCode== AutocompleteActivity.RESULT_ERROR)
            {
                val status=Autocomplete.getStatusFromIntent(data!!)
                searchBar.setText("Error")
            }
        }

    }

    fun setStartingOrEnding(type:String,place:Place, model:DirectionsViewModel){
       if (type == "Origin")
       {
           onActivityResult()
       }
        if (type == "Destination")
        {
            binding.startLocationButton.text = place.name
            model.setOriginCompletely(place.name!!,place.latLng!!,place.address!!)
        }
    }
    fun switchButtons(){
        directionsViewModel.setOriginLocation(directionsViewModel.getDestinationLocation()!!)
        directionsViewModel.setDestinationLocation(directionsViewModel.getOriginLocation()!!)
        directionsViewModel.setOriginName(directionsViewModel.getDestinationName()!!)
        directionsViewModel.setDestinationName(directionsViewModel.getOriginName()!!)
        directionsViewModel.setOriginAddress(directionsViewModel.getDestinationAddress()!!)
        directionsViewModel.setDestinationAddress(directionsViewModel.getOriginAddress()!!)
    }
}
