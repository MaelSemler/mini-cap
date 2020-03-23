package com.soen390.conumap.ui.calendar

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.soen390.conumap.event.Event
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.soen390.conumap.R
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
        val root = inflater.inflate(R.layout.calendar_fragment, container, false)
        val nextClassButton = root.findViewById<View>(R.id.coming_up)
        val classNumber = root.findViewById<TextView>(R.id.class_number_value)
        val timeValue = root.findViewById<TextView>(R.id.time_value)
        val locationValue = root.findViewById<TextView>(R.id.location_value)

        // The following will allow the next event to refresh with whatever the user's current event is when the Calendar Fragmnent is opened.
        // TODO: Implemkent way to obtain user's current event from the Calendar.
        classNumber.setText("SOEN 345 - Lecture")
        timeValue.setText("2:45 PM - 4:00 PM")
        locationValue.setText("H 937")
        nextClassButton.setOnClickListener{
            //Button so that the user can refresh the next event page if they are still on it.
            // TODO: Implement way to obtain user's current event from the Calendar.
            classNumber.setText("COMP 351 - Lab")
            timeValue.setText("2:45 PM - 6:00 PM")
            locationValue.setText("FG B030")
        }
        init()
        activity!!.supportFragmentManager.beginTransaction().add(R.id.calendar_container,containee).commit()
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun init() {
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity)
        if (account != null) {
            containee = CalendarScheduleFragment()
        }
        else{
            containee = CalendarLoginFragment()
        }

    }
}
