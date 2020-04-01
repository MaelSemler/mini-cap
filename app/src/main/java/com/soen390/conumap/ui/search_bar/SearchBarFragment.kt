package com.soen390.conumap.ui.search_bar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.soen390.conumap.R
import kotlinx.android.synthetic.main.search_bar_fragment.*


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
        val search_button = root.findViewById<View>(R.id.search_button) as Button

        search_button.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchResultsFragment)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

        val model: SearchBarViewModel by activityViewModels()
        val destination = model.getDestination()
        search_button.setText(destination)


    }



}