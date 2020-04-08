package com.soen390.conumap.ui.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soen390.conumap.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_indoor.*

class IndoorFragment : Fragment() {

    private lateinit var indoorViewModel: IndoorViewModel
//    lateinit var binding: IndoorFragmentBinding

//    lateinit var context = getContext()


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
