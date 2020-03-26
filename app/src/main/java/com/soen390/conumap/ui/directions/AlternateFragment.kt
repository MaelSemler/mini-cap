package com.soen390.conumap.ui.directions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.databinding.AlternateFragmentBinding
import com.soen390.conumap.path.path
import kotlinx.android.synthetic.main.nav_header_main.*


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

        binding.DirectionsTextBox.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Toast.makeText(getActivity(), "AlternateFragment: Touch coordinates : " +  event.x.toString() + " x " + event.y.toString(), Toast.LENGTH_SHORT).show()
                // Change route
                if ( event.y < 300 ){
                    //First line selected
                    if (path.getAlternatives() == 0){
                        path.setAlternativeRoute(1)
                    } else {
                        path.setAlternativeRoute(0)
                    }
                } else {
                    //Second Line selected
                    if (path.getAlternatives() == 2){
                        path.setAlternativeRoute(1)
                    } else {
                        path.setAlternativeRoute(2)
                    }
                }
                // Save Context
                val route_id = path.getAlternatives()
                val transportation_mode = path.getTransportationMode()
                // Reset data in view model
                path.resetDirections()
                // Restore direction selections
                path.setAlternativeRoute(route_id)
                path.setTransportationMode(transportation_mode)
                path.findDirections(activity!!)
                Toast.makeText(getActivity(), "AlternateFragment: Route changed to " + path.getAlternatives(), Toast.LENGTH_SHORT).show()
            }

            true
        })
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
