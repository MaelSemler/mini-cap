package com.soen390.conumap.ui.calendar_login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.soen390.conumap.R

class CalendarLoginFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarLoginFragment()
    }

    private lateinit var viewModel: CalendarLoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarLoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
