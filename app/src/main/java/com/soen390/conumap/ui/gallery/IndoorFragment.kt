package com.soen390.conumap.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soen390.conumap.R

class IndoorFragment : Fragment() {

    private lateinit var indoorViewModel: IndoorViewModel
    lateinit var binding: IndoorFragmentBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        indoorViewModel =
                ViewModelProviders.of(this).get(IndoorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_indoor, container, false)
        return root
    }
}
