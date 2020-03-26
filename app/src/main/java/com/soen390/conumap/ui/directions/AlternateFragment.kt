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
import com.soen390.conumap.databinding.AlternateFragmentBinding
import com.soen390.conumap.path.path

class AlternateFragment : Fragment() {

    companion object {
        fun newInstance() = AlternateFragment()
    }

    private lateinit var viewModel: AlternateViewModel
    lateinit var binding : AlternateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val alternateViewModel = ViewModelProviders.of(this)
            .get(AlternateViewModel::class.java)

        //This permit to inflate the fragment
        binding = DataBindingUtil.inflate<AlternateFragmentBinding>(inflater, R.layout.alternate_fragment, container, false).apply {
            this.setLifecycleOwner(activity)
            this.viewmodel = alternateViewModel
        }

        // Select radio button corresponding to transportation mode active
        when(path.getTransportationMode()){
            "driving" -> binding.transportationCar.setChecked(true)
            "bicycling" -> binding.transportationBike.setChecked(true)
            "walking" -> binding.transportationWalk.setChecked(true)
            "transit" -> binding.transportationBus.setChecked(true)
        }

        //To change starting and ending location, just click on the buttons to be sent to a search fragment
        binding.directionsButton.setOnClickListener{
            //TODO: send info to the search bar (DirectionSearchFragment)
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_directionsFragment)
        }

        binding.returnButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_alternateFragment_to_searchBarFragment)
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
        viewModel = ViewModelProviders.of(this).get(AlternateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
