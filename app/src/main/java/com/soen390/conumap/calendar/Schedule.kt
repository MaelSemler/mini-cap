package com.soen390.conumap.calendar

import android.app.Activity
import android.content.Context
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
//import com.google.api.client.util.DateTime
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import java.time.DayOfWeek
import java.util.*
import java.util.Calendar.SUNDAY


object Schedule {
    var mCredential: GoogleAccountCredential? = null
    var calendar: Calendar? = null

    val PREF_ACCOUNT_NAME = "accountName"//TODO: figure out if this is needed

    fun initCredentials(activity: Activity) {
        mCredential = GoogleAccountCredential.usingOAuth2(
            activity.applicationContext,
            arrayListOf(CalendarScopes.CALENDAR))
            .setBackOff(ExponentialBackOff())
    }

    fun setUpCredentials(activity: Activity, name: String){
        val accountName = name
        if (accountName != null) {
            val settings = activity!!.getPreferences(Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(PREF_ACCOUNT_NAME, accountName)
            editor.apply()
            mCredential!!.selectedAccountName = accountName
        }
    }

    fun setUpCalendar(){
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        calendar = Calendar.Builder(
            transport, jsonFactory, mCredential)
            .setApplicationName("Google Calendar API Android Quickstart")
            .build()
    }

    //Gets the events for the selected week
    fun getWeekEvents(weekCount: Int): ArrayList<String> {

        val cal = java.util.Calendar.getInstance()
        cal.set(java.util.Calendar.DAY_OF_WEEK,java.util.Calendar.SUNDAY)//Sets the date to the Sunday of this week (previous)
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
        cal.set(java.util.Calendar.MINUTE, 0)
        cal.set(java.util.Calendar.SECOND, 0)
        cal.add(java.util.Calendar.DATE,7 * weekCount)//Gets the Sunday of the selected week
        val minTime = DateTime(cal.time)
        cal.add(java.util.Calendar.DATE,6)//Gets the Saturday of the selected week
        val maxTime = DateTime(cal.time)
        val eventStrings = ArrayList<String>()

        val events = calendar!!.events().list("primary")
            .setTimeMin(minTime)
            .setTimeMax(maxTime)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items = events.items

        for (event in items) {
            var start = event.start.dateTime
            var end = event.end.dateTime
            var location = event.location
            if (start == null) {
                start = event.start.date
            }

            eventStrings.add(String.format("%s|%s|%s|%s", event.summary, start, end, location))
        }

        return eventStrings

    }

    fun getNextEvent(): String {
        val now = DateTime(System.currentTimeMillis())
        var nextEvent:String = ""
        val events = calendar!!.events().list("primary")
            .setMaxResults(1)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items = events.items
        val event =items.first()

            var start = event.start.dateTime
            var end = event.end.dateTime
            var location = event.location
            if (start == null) {
                start = event.start.date
            }
            /*if (event.endTimeUnspecified){
                end = event.end.date
            }*/
        nextEvent = String.format("%s|%s|%s|%s", event.summary, start, end, location)


        return nextEvent
    }



}