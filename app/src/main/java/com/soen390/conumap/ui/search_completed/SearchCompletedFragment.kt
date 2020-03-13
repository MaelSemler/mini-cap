package com.soen390.conumap.ui.search_completed

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.soen390.conumap.R

class SearchCompletedFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchCompletedFragment()
    }

    private lateinit var viewModel: SearchCompletedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_completed_fragment, container, false)

        //TODO: send the result of the search
        val go_button = root.findViewById<View>(R.id.go_button)
        val restart_button = root.findViewById<View>(R.id.restart_button)
        go_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_directionsFragment)
        }
        restart_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchCompletedFragment_to_searchBarFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchCompletedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
