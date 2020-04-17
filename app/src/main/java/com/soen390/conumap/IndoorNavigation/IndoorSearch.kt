package com.soen390.conumap.IndoorNavigation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.soen390.conumap.R

class IndoorSearch : Fragment() {

    companion object {
        fun newInstance() = IndoorSearch()
    }

    private lateinit var viewModel: IndoorSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.indoor_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IndoorSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
