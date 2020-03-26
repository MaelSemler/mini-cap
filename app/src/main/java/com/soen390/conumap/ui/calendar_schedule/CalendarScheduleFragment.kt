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
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event

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

    private lateinit var sundayEventLayout: RelativeLayout
    private lateinit var mondayEventLayout: RelativeLayout
    private lateinit var tuesdayEventLayout: RelativeLayout
    private lateinit var wednesdayEventLayout: RelativeLayout
    private lateinit var thursdayEventLayout: RelativeLayout
    private lateinit var fridayEventLayout: RelativeLayout
    private lateinit var saturdayEventLayout: RelativeLayout

    private val REQUEST_AUTHORIZATION = 1001
    private var classNumber:String = ""
    private var time: String = ""
    private var location: String = ""
    private lateinit var classNumberValue: TextView
    private lateinit var timeValue: TextView
    private lateinit var locationValue: TextView
    private var weekCount = 0//Keeps track of which week to show on the schedule (0: this week, 1: next week, -1: last week)

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

        sundayEventLayout = root.findViewById<RelativeLayout>(R.id.sunday)
        mondayEventLayout = root.findViewById<RelativeLayout>(R.id.monday)
        tuesdayEventLayout = root.findViewById<RelativeLayout>(R.id.tuesday)
        wednesdayEventLayout = root.findViewById<RelativeLayout>(R.id.wednesday)
        thursdayEventLayout = root.findViewById<RelativeLayout>(R.id.thursday)
        fridayEventLayout = root.findViewById<RelativeLayout>(R.id.friday)
        saturdayEventLayout = root.findViewById<RelativeLayout>(R.id.saturday)

        classNumberValue = root.findViewById<TextView>(R.id.class_number_value)
        timeValue = root.findViewById<TextView>(R.id.time_value)
        locationValue = root.findViewById<TextView>(R.id.location_value)

        NextEventRequestTask().execute()//makes the Coming Up UI
        ScheduleRequestTask().execute()//makes the Schedule UI

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

        //Do in sprint 4
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

    }

    private fun upDateCalendarUI(){//TODO: maybe look into LocalDate Class to see if it makes stuff simple
        //Change to LocalDate tomorrow = LocalDate.now().plusDays(1);
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
        cal.add(java.util.Calendar.SECOND,-1)//makes the end dates Saturday at 23:59:59, useful for the endMonth and year
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

    private fun showComingUp(results: com.soen390.conumap.event.Event?){//Todo: make it take in an event DONE MAYBE
        /*Log.d("QUESTIONMARK", "Made it to onCalendarRequestTaskCompleted!")
        var firstEventArray = results.split("|").toTypedArray()
        classNumber = firstEventArray[0]
        if (firstEventArray[2] != null){
            // Format for time for events with a start and end time.
            time = firstEventArray[1] +"-" + firstEventArray[2]
        }
        else{
            // Format for time for all day events.
            time = firstEventArray[1]
        }
        location = firstEventArray[3]
        */
        var classNumber = results!!.eventName +" - " + results.roomNumber
        var time = results.startTime + " - " + results.endTime
        classNumberValue.setText(classNumber)
        timeValue.setText(time)
        locationValue.setText(results.roomLocation)

    }

    private fun showSchedule(eventList: MutableList<Event>){

        var layout: RelativeLayout
        for(event in eventList){
            val date = java.util.Calendar.getInstance()
            date.timeInMillis = event.start.dateTime.value

             layout = when(date.get(java.util.Calendar.DAY_OF_WEEK)){
                Calendar.SUNDAY -> sundayEventLayout
                Calendar.MONDAY -> mondayEventLayout
                Calendar.TUESDAY -> tuesdayEventLayout
                Calendar.WEDNESDAY -> wednesdayEventLayout
                Calendar.THURSDAY -> thursdayEventLayout
                Calendar.FRIDAY -> fridayEventLayout
                Calendar.SATURDAY -> saturdayEventLayout
                else -> RelativeLayout(null)//Todo: figure ut if this else if necessary since we already go through all the possible weekday options
            }

            val eventButton: Button = Button(context!!)
            val dp = context!!.resources.displayMetrics.density;//in the schedule 1dp = to one minute
            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                durationToHeight(date, event).toInt()//Todo: better way?
            )

            param.topMargin = timeToMargin(date).toInt()//Todo: better way?
            eventButton.text = event.summary//Todo: have the proper text shown here, like in the mockups
            eventButton.textSize = 10f
            eventButton.layoutParams = param
            layout.addView(eventButton)
        }
    }

    private fun clearAllEventsUI(){
        sundayEventLayout.removeAllViews()
        mondayEventLayout.removeAllViews()
        tuesdayEventLayout.removeAllViews()
        wednesdayEventLayout.removeAllViews()
        thursdayEventLayout.removeAllViews()
        fridayEventLayout.removeAllViews()
        saturdayEventLayout.removeAllViews()
    }

    private fun timeToMargin(date: Calendar) : Float{
        val hour = date.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = date.get(java.util.Calendar.MINUTE)
        val dp = context!!.resources.displayMetrics.density;
        return (hour * 60 + minute) * dp
    }

    private fun durationToHeight(date: Calendar, event: Event): Float{
        val endDate = java.util.Calendar.getInstance()
        endDate.timeInMillis = event.end.dateTime.value

        val startHour = date.get(java.util.Calendar.HOUR_OF_DAY)
        val startMinute = date.get(java.util.Calendar.MINUTE)

        val endHour = endDate.get(java.util.Calendar.HOUR_OF_DAY)
        val endMinute = endDate.get(java.util.Calendar.MINUTE)

        val dp = context!!.resources.displayMetrics.density;
        return ((endHour * 60 + endMinute) - (startHour * 60 + startMinute)) * dp

    }

    private inner class ScheduleRequestTask() : AsyncTask<Void, Void, MutableList<Event>>() {
        private var mLastError: Exception? = null

        override fun doInBackground(vararg params: Void): MutableList<Event>? {
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
            clearAllEventsUI()
            upDateCalendarUI()
        }

        override fun onPostExecute(output: MutableList<Event>?) {

                showSchedule(output!!)
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))

                //scheduleBuild(output)
                //onCalendarRequestTaskCompleted(output)

        }

        override fun onCancelled() {//Todo: proprely handle this or delete it

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
    private inner class NextEventRequestTask() : AsyncTask<Void, Void, com.soen390.conumap.event.Event>() {//Todo: make it return an event
        private var mLastError: Exception? = null

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        override fun doInBackground(vararg params: Void): com.soen390.conumap.event.Event? {
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

        override fun onPostExecute(result: com.soen390.conumap.event.Event?) {
            Log.d("QUESTIONMARK", "onPostExecute")
            if (result == null || result == null) {
                debugText.text = "No results returned."
            } else {
                //output.add(0, "Data retrieved using the Google Calendar API:")
                //debugText.text = (TextUtils.join("\n", output))
                //scheduleBuild(output)
                showComingUp(result)
            }
        }

        override fun onCancelled() {//Todo: proprely handle this or delete it

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