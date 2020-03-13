package com.soen390.conumap.ui.search_results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

        val cancel_button = root.findViewById<View>(R.id.cancel_search)
        val clear_button = root.findViewById<View>(R.id.clear_input)
        val search_bar = root.findViewById<View>(R.id.edSearchResults) as EditText
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                NavHostFragment.findNavController(this).navigate(R.id.action_searchResultsFragment_to_searchCompletedFragment)
            }
            false
        })

        clear_button.setOnClickListener{
            search_bar.setText("")
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
