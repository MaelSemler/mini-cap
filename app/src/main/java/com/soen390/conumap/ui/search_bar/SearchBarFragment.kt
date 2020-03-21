package com.soen390.conumap.ui.search_bar


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button

import androidx.navigation.fragment.NavHostFragment


import com.soen390.conumap.R
import kotlinx.android.synthetic.main.nav_header_main.*

class SearchBarFragment : Fragment() {

    companion object {
        fun newInstance() = SearchBarFragment()
    }

    private lateinit var viewModel: SearchBarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_bar_fragment, container, false)

        //This is the search bar
        val search_button = root.findViewById<View>(R.id.search_button) as Button
        search_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchBarViewModel::class.java)
        // TODO: Use the ViewModel
    }



}