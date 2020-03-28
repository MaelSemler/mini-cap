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
import com.soen390.conumap.path.Path
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
                    if (Path.getAlternatives() == 0){
                        Path.setAlternativeRoute(1)
                    } else {
                        Path.setAlternativeRoute(0)
                    }
                } else {
                    //Second Line selected
                    if (Path.getAlternatives() == 2){
                        Path.setAlternativeRoute(1)
                    } else {
                        Path.setAlternativeRoute(2)
                    }
                }
                // Save Context
                val route_id = Path.getAlternatives()
                // val transportation_mode = path.getTransportationMode() // waiting for transportation mode available
                // Reset data in view model
                Path.resetDirections()
                // Restore direction selections
                Path.setAlternativeRoute(route_id)
                // Path.setTransportationMode(transportation_mode) // waiting for transportation mode available
                Path.findDirections(activity!!)
                Toast.makeText(getActivity(), "AlternateFragment: Route changed to " + Path.getAlternatives(), Toast.LENGTH_SHORT).show()
            }

            true
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlternateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
