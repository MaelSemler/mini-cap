package com.soen390.conumap.ui.calendar_schedule

import android.accounts.AccountManager
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.util.DateTime
import com.soen390.conumap.Directions.directions

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_login.CalendarLoginFragment
import java.util.*

class CalendarScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarScheduleFragment()
    }

    private lateinit var viewModel: CalendarScheduleViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var debugText: TextView

    private lateinit var startDate: DateTime
    private lateinit var endDate: DateTime

    private lateinit var monthTitle: TextView
    private lateinit var sundayDate: TextView
    private lateinit var mondayDate: TextView
    private lateinit var tuesdayDate: TextView
    private lateinit var wednesdayDate: TextView
    private lateinit var thursdayDate: TextView
    private lateinit var fridayDate: TextView
    private lateinit var saturdayDate: TextView

    val REQUEST_GOOGLE_PLAY_SERVICES = 1002
    val REQUEST_AUTHORIZATION = 1001
    var classNumber:String = ""
    var time: String = ""
    var location: String = ""
    lateinit var classNumberValue: TextView
    lateinit var timeValue: TextView
    lateinit var locationValue: TextView
    var weekCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.calendar_schedule_fragment, container, false)
        val signOutButton = root.findViewById<View>(R.id.debug_sign_out)
        val nextWeekButton = root.findViewById<View>(R.id.next_week)
        val previousWeekButton = root.findViewById<View>(R.id.previous_week)
        val goNowButton = root.findViewById<View>(R.id.go_now_button)
        debugText = root.findViewById<View>(R.id.debug_text) as TextView

        monthTitle= root.findViewById<View>(R.id.month_year) as TextView
        sundayDate = root.findViewById<View>(R.id.sunday_date) as TextView
        mondayDate = root.findViewById<View>(R.id.monday_date) as TextView
        tuesdayDate = root.findViewById<View>(R.id.tuesday_date) as TextView
        wednesdayDate = root.findViewById<View>(R.id.wednesday_date) as TextView
        thursdayDate = root.findViewById<View>(R.id.thursday_date) as TextView
        fridayDate = root.findViewById<View>(R.id.friday_date) as TextView
        saturdayDate = root.findViewById<View>(R.id.saturday_date) as TextView
        NextEventRequestTask().execute()//makes the Coming Up UI
        ScheduleRequestTask().execute()//makes the Schedule UI
        classNumberValue = root.findViewById<TextView>(R.id.class_number_value)
        timeValue = root.findViewById<TextView>(R.id.time_value)
        locationValue = root.findViewById<TextView>(R.id.location_value)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        val nameAccount = mGoogleSignInClient.signInIntent.getStringArrayExtra((AccountManager.KEY_ACCOUNT_NAME))

        signOutButton.setOnClickListener {
            signOut()
        }
        nextWeekButton.setOnClickListener {
            weekCount++
            val nextWeekTask: ScheduleRequestTask = ScheduleRequestTask()
            nextWeekTask.execute()

        }
        previousWeekButton.setOnClickListener {
            weekCount--
            val previousWeekTask: ScheduleRequestTask = ScheduleRequestTask()
            previousWeekTask.execute()

        }
        //maybe set it after the data is collected
        goNowButton.setOnClickListener{
            //Close the Calendar
            //get the directions

            /*if(goNowEvent != null){
            *   directions(goNowEvent.location)
            *   closeCalendar()
            * }
            *
            * */
        }
        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarScheduleViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun upDateCalendarUI(){
        val cal = java.util.Calendar.getInstance()
        cal.set(java.util.Calendar.DAY_OF_WEEK,java.util.Calendar.SUNDAY)//Sets the date to the Sunday of this week (previous)
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
        cal.set(java.util.Calendar.MINUTE, 0)
        cal.set(java.util.Calendar.SECOND, 0)
        cal.add(java.util.Calendar.DATE,7 * weekCount)//Gets the Sunday of the selected week
        startDate = DateTime(cal.time)//the week starts on Sunday at midnight
        val startMonth =  cal.getDisplayName(java.util.Calendar.MONTH,java.util.Calendar.LONG, Locale.getDefault())
        val startYear = cal.get(java.util.Calendar.YEAR)

        sundayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        mondayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        tuesdayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        wednesdayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        thursdayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        fridayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)
        saturdayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()
        cal.add(java.util.Calendar.DATE,1)//Gets the Sunday of the selected week

        endDate = DateTime(cal.time)//the week ends on the next Sunday at midnight
        val endMonth = cal.getDisplayName(java.util.Calendar.MONTH,java.util.Calendar.LONG, Locale.getDefault())
        val endYear = cal.get(java.util.Calendar.YEAR)

        if(startYear != endYear){
            monthTitle.text = String.format("%s %s - %s %s", startMonth, startYear, endMonth, endYear)
        }
        else if(startMonth != endMonth){
            monthTitle.text = String.format("%s - %s %s", startMonth, endMonth, startYear)
        }
        else{
            monthTitle.text = String.format("%s %s", startMonth, startYear)
        }


    }
    private fun signOut(){
        mGoogleSignInClient.signOut()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.calendar_container,CalendarLoginFragment.newInstance()).commit()
    }
/*
    fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val dialog = apiAvailability.getErrorDialog(
            activity!!,
            connectionStatusCode,
            REQUEST_GOOGLE_PLAY_SERVICES)
        dialog.show()
    }*/

    private fun showComingUp(results: String){
        Log.d("QUESTIONMARK", "Made it to onCalendarRequestTaskCompleted!")
        var firstEventArray = results.split("|").toTypedArray()
        classNumber = firstEventArray[0]
        time = firstEventArray[1] +"-" + firstEventArray[2]
        location = firstEventArray[3]

        classNumberValue.setText(classNumber)
        timeValue.setText(time)
        locationValue.setText(location)

    }

    private fun showSchedule(events: MutableList<String>){
        /*Todo:
        *  there are 7 relative layouts, one for each day of the week
        *  each layout needs to be filled with buttons
        *  maybe pass the event objects directly instead of the string
        *
        * */
        /*
        when (Date){
            Sunday -> layout =view!!.findViewById<RelativeLayout>(R.id.sunday)
            Monday -> layout =view!!.findViewById<RelativeLayout>(R.id.monday)
            Tuesday -> layout =view!!.findViewById<RelativeLayout>(R.id.tuesday)
            ...
            showDay(layout)

        }*/

        val layout =view!!.findViewById<RelativeLayout>(R.id.sunday)
        showDay(layout,events)


    }

    fun showDay(layout: RelativeLayout, events: MutableList<String>){
        layout.removeAllViews() //Clears that day in the schedule

        for(items in events){
            val progButton: Button = Button(this.context)
            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            param.topMargin = 100//Todo: get start time, get midnight - start time, get the diference in seconds, and 1 minute is 1 margin
            progButton.layoutParams = param
            progButton.setText(items)//Todo: have the proper text shown here, like in the mockups
            progButton.textSize = 10f
            progButton.height = 100//Todo: get the duration in minutes 1 minute
            layout.addView(progButton)
        }
    }


    private inner class ScheduleRequestTask() : AsyncTask<Void, Void, MutableList<String>>() {
        private var mLastError: Exception? = null

        override fun doInBackground(vararg params: Void): MutableList<String>? {
            Log.d("QUESTIONMARK", "doInBackground")
            try {
                return Schedule.getWeekEvents(startDate,endDate)
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                return null
            }

        }


        override fun onPreExecute() {
            Log.d("QUESTIONMARK", "onPreExecute")
            debugText.text = "loading...tool long? sign out"
            upDateCalendarUI()
        }

        override fun onPostExecute(output: MutableList<String>?) {

                showSchedule(output!!)
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))

                //scheduleBuild(output)
                //onCalendarRequestTaskCompleted(output)

        }

        override fun onCancelled() {

            if (mLastError != null) {
                if (mLastError is GooglePlayServicesAvailabilityIOException) {
                    /*showGooglePlayServicesAvailabilityErrorDialog(
                        (mLastError as GooglePlayServicesAvailabilityIOException)
                            .connectionStatusCode)*/
                } else if (mLastError is UserRecoverableAuthIOException) {
                    startActivityForResult(
                        (mLastError as UserRecoverableAuthIOException).intent,
                        REQUEST_AUTHORIZATION)
                } else {
                    debugText.text = "The following error occurred:\n" + mLastError!!.message
                }
            } else {
                debugText.text = "Request cancelled."
            }
        }
    }
    private inner class NextEventRequestTask() : AsyncTask<Void, Void, String>() {
        private var mLastError: Exception? = null


        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        override fun doInBackground(vararg params: Void): String? {
            Log.d("QUESTIONMARK", "doInBackground")
            try {
                return Schedule.getNextEvent()
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                return null
            }

        }


        override fun onPreExecute() {
            Log.d("QUESTIONMARK", "onPreExecute")
            debugText.text = "loading...tool long? sign out"

        }

        override fun onPostExecute(output: String?) {
            Log.d("QUESTIONMARK", "onPostExecute")
            if (output == null || output == null) {
                debugText.text = "No results returned."
            } else {
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))
                //scheduleBuild(output)
                showComingUp(output)
            }
        }

        override fun onCancelled() {

            if (mLastError != null) {
                if (mLastError is GooglePlayServicesAvailabilityIOException) {
                    /*showGooglePlayServicesAvailabilityErrorDialog(
                        (mLastError as GooglePlayServicesAvailabilityIOException)
                            .connectionStatusCode)*/
                } else if (mLastError is UserRecoverableAuthIOException) {
                    startActivityForResult(
                        (mLastError as UserRecoverableAuthIOException).intent,
                        REQUEST_AUTHORIZATION)
                } else {
                    debugText.text = "The following error occurred:\n" + mLastError!!.message
                }
            } else {
                debugText.text = "Request cancelled."
            }
        }
    }

}