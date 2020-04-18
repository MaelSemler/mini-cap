package com.soen390.conumap.ui.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        val hfloor_nine_button = root.findViewById<View>(R.id.hfloor_nine_button)
        val hfloor_eight_button = root.findViewById<View>(R.id.hfloor_eight_button)

        hfloor_nine_button.setOnClickListener{
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorPrimary) }?.let { it2 ->
                hfloor_nine_button.setBackgroundColor(
                    it2
                )
            }
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            print("\n---------------AAAAAAAAAAAAAA")
        }

        hfloor_eight_button.setOnClickListener{
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.colorPrimary) }?.let { it2 ->
                hfloor_eight_button.setBackgroundColor(
                    it2
                )
            }
            print("\n---------------BBBBBBBBBBBBBBB")
        }

        return root
    }
}
