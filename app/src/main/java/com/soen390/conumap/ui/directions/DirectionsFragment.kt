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
//        val root = inflater.inflate(R.layout.directions_fragment, container, false)
        //TODO:show the steps of the Direction in a subfragment
        //TODO: get the results from SearchCompletedFragment
        //TODO: get the currentlocation
        //TODO: add the name of the result and the curent location to the start and end buttons (get them from the actual objects)

        val directionViewModel = ViewModelProviders.of(this)
            .get(DirectionsViewModel::class.java)

        //TODO: Only binding that would allow us to show the steps
//        binding = DataBindingUtil.setContentView<DirectionsFragmentBinding>(activity!!, R.layout.directions_fragment).apply{
//            this.setLifecycleOwner(activity)
//            this.viewmodel = directionViewModel
//
//        }

        //TODO: THIS DOES NOT WORK
        binding = DataBindingUtil.inflate<DirectionsFragmentBinding>(inflater, R.layout.directions_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = directionViewModel
        }



//        val start_button = root.findViewById<View>(R.id.start_location_button)
//        val end_button = root.findViewById<View>(R.id.end_location_button)
//        val return_button = root.findViewById<View>(R.id.return_button)
//        val switch_button = root.findViewById<View>(R.id.switch_button)//TODO: impletment the swap locations feature

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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DirectionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
