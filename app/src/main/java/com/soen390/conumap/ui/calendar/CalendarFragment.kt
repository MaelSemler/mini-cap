package com.soen390.conumap.ui.calendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_login.CalendarLoginFragment
import com.soen390.conumap.ui.calendar_schedule.CalendarScheduleFragment

class CalendarFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarFragment()

    }
    private lateinit var containee: Fragment
    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initSubFragment()
        activity!!.supportFragmentManager.beginTransaction().add(R.id.calendar_container,containee).commit()
        return inflater.inflate(R.layout.calendar_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    //Check which fragment should be added depending on connected accounts
    private fun initSubFragment() {
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity)
        if (account != null) {
            Schedule.setName(account.email)
            containee = CalendarScheduleFragment()
        }
        else{
            containee = CalendarLoginFragment()
        }

    }
}