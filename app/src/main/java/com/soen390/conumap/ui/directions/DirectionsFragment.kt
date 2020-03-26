package com.soen390.conumap.ui.directions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment

import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.path.path

class DirectionsFragment : Fragment() {

    companion object {
        fun newInstance() = DirectionsFragment()
    }

    private lateinit var viewModel: DirectionsViewModel
    lateinit var binding : DirectionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val root = inflater.inflate(R.layout.directions_fragment, container, false)
        //TODO: get the results from SearchCompletedFragment
        //TODO: get the currentlocation
        //TODO: add the name of the result and the curent location to the start and end buttons (get them from the actual objects)

        val directionViewModel = ViewModelProviders.of(this)
            .get(DirectionsViewModel::class.java)

/*
        val walkButton = root.findViewById<View>(R.id.transportation_walk) as RadioButton
        val busButton = root.findViewById<View>(R.id.transportation_bus) as RadioButton
        val carButton = root.findViewById<View>(R.id.transportation_car) as RadioButton
        val bikeButton= root.findViewById<View>(R.id.transportation_bike) as RadioButton
*/
        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = directionViewModel
        }

        // Select radio button corresponding to transportation mode active
        when(path.getTransportationMode()){
            "driving" -> binding.transportationCar.setChecked(true)
            "bicycling" -> binding.transportationBike.setChecked(true)
            "walking" -> binding.transportationWalk.setChecked(true)
            "transit" -> binding.transportationBus.setChecked(true)
        }

        //To change starting and ending location, just click on the buttons to be sent to a search fragment
        binding.startLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_directionsSearchFragment)
        }
        binding.endLocationButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_directionsSearchFragment)
        }
        //TODO: look in if this is the best way to implement a "back" function
        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_searchBarFragment)
        }
        // Alternate Routes
        binding.altButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_alternateFragment)
        }

        // Get radio group selected status and text using button click event
        binding.transportationBike.setOnClickListener{
            path.resetDirections()
            path.setTransportationCycling()
            path.findDirections(activity!!)
        }
        binding.transportationBus.setOnClickListener{
            path.resetDirections()
            path.setTransportationTransit()
            path.findDirections(activity!!)
        }
        binding.transportationCar.setOnClickListener{
            path.resetDirections()
            path.setTransportationDriving()
            path.findDirections(activity!!)
        }
        binding.transportationWalk.setOnClickListener{
            path.resetDirections()
            path.setTransportationWalking()
            path.findDirections(activity!!)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

    }


}
