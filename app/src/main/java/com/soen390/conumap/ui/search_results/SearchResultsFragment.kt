package com.soen390.conumap.ui.search_results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment

import com.soen390.conumap.R
import kotlinx.android.synthetic.main.nav_header_main.view.*

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

        //TODO: send the result of the search

        //Getting the Views from the fragment
        val cancel_button = root.findViewById<View>(R.id.cancel_search) as ImageButton
        val clear_button = root.findViewById<View>(R.id.clear_input) as ImageButton
        val search_bar = root.findViewById<View>(R.id.edSearchResults) as EditText

        //This is the search bar edit text
        //this method waits for the "ENTER" key to be pressed
        //It changes fragment when it is pressed
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
            }
            false
        })

        //This button clears the edit text input when it is pressed
        clear_button.setOnClickListener{
            search_bar.setText("")
        }

        //This button goes to the previous fragment
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
