package com.soen390.conumap.ui.calendar_schedule

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.util.DateTime
import com.soen390.conumap.event.Event

import com.soen390.conumap.R
import com.soen390.conumap.calendar.Schedule
import com.soen390.conumap.ui.calendar_login.CalendarLoginFragment
import java.util.*
import java.util.concurrent.TimeUnit


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

    private lateinit var classNumberValue: TextView
    private lateinit var timeValue: TextView
    private lateinit var roomValue: TextView
    private lateinit var locationValue: TextView
    private lateinit var calendarDropDown: Spinner


    private var weekCount: Int = 0//Keeps track of which week to show on the schedule (0: this week, 1: next week, -1: last week)
    private var dp: Float = 0F //The logical density of the display
    private var calendarID: Int= 0//index of the different google calendars

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
        roomValue = root.findViewById<TextView>(R.id.room_location_value)
        locationValue = root.findViewById<TextView>(R.id.location_value)
        calendarDropDown = root.findViewById<Spinner>(R.id.dropdown_calendar)

        CalendarListRequestTask().execute()

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        dp = context!!.resources.displayMetrics.density//The logical density of the display

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

        goNowButton.setOnClickListener{
            NavHostFragment.findNavController(this).navigateUp()
            //route(currentLocation, Destination)

        }

        calendarDropDown.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calendarID = position
                NextEventRequestTask().execute()//makes the Coming Up UI
                ScheduleRequestTask().execute()//makes the Schedule UI]
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarScheduleViewModel::class.java)

    }

    //Signs out the google account and goes back to the map
    private fun signOut(){
        mGoogleSignInClient.signOut()
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.calendar_container,CalendarLoginFragment.newInstance()).commit()
    }

    //Clears all the events (buttons) from the schedule
    private fun clearEventUI(){
        sundayEventLayout.removeAllViews()
        mondayEventLayout.removeAllViews()
        tuesdayEventLayout.removeAllViews()
        wednesdayEventLayout.removeAllViews()
        thursdayEventLayout.removeAllViews()
        fridayEventLayout.removeAllViews()
        saturdayEventLayout.removeAllViews()
    }
    //Clears all the text from the Coming Up section
    private fun clearComingUpUI(){
        classNumberValue.text = ""
        timeValue.text = ""
        roomValue.text = ""
        locationValue.text = ""
    }

    //Puts the right dates on the schedule
    private fun setUpCalendarDates(){
        val cal = java.util.Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK,java.util.Calendar.SUNDAY)//Sets the date to the Sunday of this week (previous)
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
        cal.set(java.util.Calendar.MINUTE, 0)
        cal.set(java.util.Calendar.SECOND, 0)
        cal.add(java.util.Calendar.DATE,7 * weekCount)//Gets the Sunday of the selected week
        startDate = DateTime(cal.time)//the week starts on Sunday at midnight
        val startMonth =  cal.getDisplayName(java.util.Calendar.MONTH,java.util.Calendar.LONG, Locale.getDefault())
        val startYear = cal.get(java.util.Calendar.YEAR)

        sundayDate.text = cal.get(java.util.Calendar.DAY_OF_MONTH).toString()//Sets the day of the week with their date
        cal.add(java.util.Calendar.DATE,1)//Goes to the next day
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

    //Adds all the different calendar names in the drop down
    fun setDropDown(calendarNameList: MutableList<String>) {
        val adapter = ArrayAdapter(context!!,android.R.layout.simple_spinner_item,calendarNameList)
        calendarDropDown.adapter = adapter
    }

    //Shows the next event information
    private fun showComingUp(results: Event?){
        if (results != null){
            classNumberValue.text = results!!.eventName
            timeValue.text = results.startTime + " - " + results.endTime
            roomValue.text = results.roomNumber
            locationValue.text = results.buildingLocation
        }
        else{
            classNumberValue.text = getString(R.string.noUpcomingEvent)
            timeValue.text = getString(R.string.notAvailable)
            roomValue.text = getString(R.string.notAvailable)
        }
    }

    //Makes and places the event in the schedule
    @SuppressLint("ResourceAsColor")
    private fun showSchedule(eventList: MutableList<Event>){
        var layout: RelativeLayout
        for(event in eventList){
            val date = Calendar.getInstance()
            date.timeInMillis = event.start!!

             layout = when(date.get(Calendar.DAY_OF_WEEK)){
                Calendar.SUNDAY -> sundayEventLayout
                Calendar.MONDAY -> mondayEventLayout
                Calendar.TUESDAY -> tuesdayEventLayout
                Calendar.WEDNESDAY -> wednesdayEventLayout
                Calendar.THURSDAY -> thursdayEventLayout
                Calendar.FRIDAY -> fridayEventLayout
                Calendar.SATURDAY -> saturdayEventLayout
                else -> RelativeLayout(null)//Needed for when but wont be reached
            }

            val eventButton: Button = Button(context!!)

            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                durationToHeight(event.duration!!).toInt()
            )

            param.topMargin = startToPosition(date).toInt()
            param.marginEnd = dp.toInt()
            param.marginStart = dp.toInt()
            eventButton.text = event.eventName
            eventButton.textSize = 10f
            eventButton.setBackgroundColor(context!!.resources.getColor(R.color.lightRed))
            eventButton.setTextColor(context!!.resources.getColor(R.color.buttonColor))
            eventButton.layoutParams = param
            layout.addView(eventButton)
        }
    }

    //Changes the time in minutes to the events position in the schedule
    private fun startToPosition(date: Calendar) : Float{
        val hour = date.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = date.get(java.util.Calendar.MINUTE)
        return (hour * 60 + minute) * dp
    }

    //Changes the duration time of the event to size of the event on the schedule
    private fun durationToHeight(duration: Long): Float{
        return TimeUnit.MILLISECONDS.toMinutes(duration) * dp

    }

    //A new Schedule is requested
    private inner class ScheduleRequestTask() : AsyncTask<Void, Void, MutableList<Event>>() {
        private var mLastError: Exception? = null
        //Resets the schedule for the new Schedule week view
        override fun onPreExecute() {
            debugText.text = "loading...tool long? sign out"
            clearEventUI()
            setUpCalendarDates()
        }
        //Gets the new events taking in consideration the week and the calendar chosen
        override fun doInBackground(vararg params: Void): MutableList<Event>? {
            return try {
                Schedule.getWeekEvents(startDate,endDate,calendarID)
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                null
            }
        }
        //Updates the new schedule
        override fun onPostExecute(output: MutableList<Event>?) {
                showSchedule(output!!)
        }

        override fun onCancelled() {
            if (mLastError != null) {
                    debugText.text = "SCHEDULE The following error occurred:\n" + mLastError!!.message
            } else {
                debugText.text = "Request cancelled."
            }
        }
    }

    //The Next event is requested
    private inner class NextEventRequestTask() : AsyncTask<Void, Void, Event>() {//Todo: make it return an event
        private var mLastError: Exception? = null
        //Clears the next event text
        override fun onPreExecute() {
            clearComingUpUI()
        }
        //Gets the next event of the calendar chosen
        override fun doInBackground(vararg params: Void): Event? {
            return try {
                Schedule.getNextEvent(calendarID)
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                null
            }
        }
        //Updates the next event
        override fun onPostExecute(result: Event?) {
            showComingUp(result)
        }

        override fun onCancelled() {
            if (mLastError != null) {
                debugText.text = "SCHEDULE The following error occurred:\n" + mLastError!!.message
                showComingUp(null)
            } else {
                debugText.text = "Request cancelled."
            }
        }
    }

    //A list of all the calendars is requested
    private inner class CalendarListRequestTask() : AsyncTask<Void, Void, MutableList<String>>() {
        //Todo: make it return an event
        private var mLastError: Exception? = null
        //Sets up and gets the list of calendars
        override fun doInBackground(vararg params: Void): MutableList<String>? {
            return try {
                Schedule.getCalendarIDs()
            } catch (e: Exception) {
                mLastError = e
                cancel(true)
                null
            }
        }
        //Sets up the drop down with the names of the calendars
        override fun onPostExecute(result: MutableList<String>?) {
            setDropDown(result!!)
        }

        override fun onCancelled() {//Todo: proprely handle this or delete it
            if (mLastError != null) {
                debugText.text = "IDs The following error occurred:\n" + mLastError!!.message
            } else {
                debugText.text = "Request cancelled."
            }

        }
    }

}