package com.soen390.conumap.ui.search_bar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.soen390.conumap.R

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

        val search_bar = root.findViewById<View>(R.id.edSearch)
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                NavHostFragment.findNavController(this).navigate(R.id.action_searchBarFragment_to_searchResultsFragment)
            }
            false
        })



        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchBarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
