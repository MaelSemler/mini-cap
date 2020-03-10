package com.soen390.conumap

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView


class TopFieldFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_top_field, container, false)
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)
        imgBack.setOnClickListener {
            val intent=Intent( activity, MapsActivity::class.java)
            startActivity(intent)

        }
        // Inflate the layout for this fragment
        return view
    }


}
