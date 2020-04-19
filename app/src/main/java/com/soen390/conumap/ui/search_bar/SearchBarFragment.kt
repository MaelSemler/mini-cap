package com.soen390.conumap.ui.search_bar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import com.soen390.conumap.ui.directions.DirectionsViewModel


class SearchBarFragment : Fragment() {


    companion object {
        fun newInstance() = SearchBarFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_bar_fragment, container, false)

        //Getting the search bar from the fragment
        val searchButton = root.findViewById<View>(R.id.search_button) as Button

        searchButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val model: DirectionsViewModel by activityViewModels()
        if (model.destinationName.value != null){
            NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchCompletedFragment)
        }
        super.onActivityCreated(savedInstanceState)

    }



}