package com.soen390.conumap.ui.search_results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.soen390.conumap.R

class SearchResultsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchResultsFragment()
    }

    private lateinit var viewModel: SearchResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.search_results_fragment, container, false)
        //TODO: modify so that when enter is pressed, then change fragment
        //TODO: send the result of the search
        val send_button = root.findViewById<View>(R.id.search_send_button)
        val cancel_button = root.findViewById<View>(R.id.cancel_button)

        send_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
        }
        //TODO: look in if this is the best way to implement a "back" function
        cancel_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigateUp()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchResultsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
