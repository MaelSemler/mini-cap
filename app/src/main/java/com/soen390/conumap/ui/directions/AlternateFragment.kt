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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlternateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
