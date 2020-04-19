package com.soen390.conumap.ui.directions

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.model.LatLng
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.path.Path
import com.soen390.conumap.map.Map
import kotlinx.android.synthetic.main.directions_fragment.*


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
            //PLEASE TEST THIS
            //directionsViewModel.originLocation.value= LatLng(45.7,-73.3)
            directionsViewModel.originLocation.value= Map.getCurrentLocation()
            Path.setOrigin(directionsViewModel.originLocation.value!!)
        }
        if (directionsViewModel.originName.value==null)
            directionsViewModel.originName.value="Current Location"

        binding.startLocationButton.setText(directionsViewModel.originName.value)
        binding.endLocationButton.setText(directionsViewModel.destinationName.value)


        Path.findDirections(requireActivity())

        // Paint Current Button
        binding.run {
            //            Path.setDestination(destinationLatLng)

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
            else -> {
                // Default transportation is car
                binding.transportationCar.setChecked(true)
                Path.setTransportation(getString(R.string.driving))
            }
        }

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

        if(!Path.getDirectionScreenMode()!!) {
            binding.DirectionsTextBox.setOnTouchListener(View.OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // Toast.makeText(getActivity(), "AlternateFragment: Touch coordinates : " +  event.x.toString() + " x " + event.y.toString(), Toast.LENGTH_SHORT).show()
                    // Change route
                    var line = 0
                    if (event.y < 210) {
                        //First line selected
                        line = 1
                    } else if (event.y < 420 ) {
                        line = 2
                    } else if (event.y < 640 ) {
                        line = 3
                    } else if (event.y < 860 ) {
                        line = 4
                    } else if (event.y < 1080 ) {
                        line = 5
                    }
                    // Toast.makeText(getActivity(), "AlternateFragment: Line selected : $line" , Toast.LENGTH_SHORT).show()
                    if (line <=  Path.getAlternatives()){
                        line = line - 1
                    }
                    // Toast.makeText(getActivity(), "AlternateFragment: Route selected : $line" , Toast.LENGTH_SHORT).show()
                    Path.setAlternativeRoute(line)
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        end_location_button.setText(directionsViewModel.destinationName.value)

    }



    fun switchButtons(){
        val originLocation= directionsViewModel.originLocation.value
        directionsViewModel.originLocation.value= directionsViewModel.destinationLocation.value
        directionsViewModel.destinationLocation.value=originLocation
        Path.setOrigin(directionsViewModel.originLocation.value!!)
        Path.setDestination(directionsViewModel.destinationLocation.value!!)

        val originName= directionsViewModel.originName.value
        directionsViewModel.originName.value= directionsViewModel.destinationName.value
        directionsViewModel.destinationName.value=originName

        val originAddress= directionsViewModel.originAddress.value
        directionsViewModel.originAddress.value= directionsViewModel.destinationAddress.value
        directionsViewModel.destinationAddress.value=originAddress
    }

}
