package com.soen390.conumap.ui.directions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.path.Path
import com.soen390.conumap.path.PathViewModel
import com.soen390.conumap.ui.search_bar.SearchBarViewModel
import kotlinx.android.synthetic.main.directions_fragment.*


class DirectionsFragment : Fragment() {

    companion object {
        fun newInstance() = DirectionsFragment()
    }

    private lateinit var viewModel: DirectionsViewModel
    lateinit var binding : DirectionsFragmentBinding
    lateinit var distanceBar: TextView
    lateinit var timeBar: TextView
    lateinit var infoBar: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: get the results from SearchCompletedFragment
        //TODO: get the currentlocation
        //TODO: add the name of the result and the current location to the start and end buttons (get them from the actual objects)
        var root = inflater.inflate(R.layout.directions_fragment, container, false)
        val directionViewModel = ViewModelProviders.of(this)
            .get(DirectionsViewModel::class.java)
        val pathviewModel: PathViewModel by activityViewModels()



        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = directionViewModel
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
            NavHostFragment.findNavController(this).navigate(R.id.action_directionsFragment_to_searchCompletedFragment)
        }
        binding.transportationWalk.setOnClickListener {   //This binds the radio button to an onclick listener event that sets the transportation mode
            pathviewModel.setTransportationMode("walking")//TODO: This does not work right now will need to be fixed
            Path.setTransportMode("walking")
            Path.findDirections(activity!!)
            //TODO: redisplay new directions
        }
        binding.transportationBike.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
//            pathviewModel.setTransportationMode("bicycling")
            Path.setTransportMode("bicycling")
            Path.findDirections(activity!!)
            //TODO: redisplay new directions
        }
        binding.transportationCar.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
//            pathviewModel.setTransportationMode("driving")
            Path.setTransportMode("driving")
            Path.findDirections(activity!!)
            //TODO: redisplay new directions
        }
        binding.transportationBus.setOnClickListener {//This binds the radio button to an onclick listener event that sets the transportation mode
//            pathviewModel.setTransportationMode("transit")
            Path.setTransportMode("transit")
            Path.findDirections(activity!!)
        }
        //TODO: enable switchOriginAndDestination button
        //TODO: implement alternative button and set the alternative here. Display the changed directions.
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)

        val model: SearchBarViewModel by activityViewModels()
        val destination = model.getDestination()

        end_location_button.setText(destination)

    }





}
