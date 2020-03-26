package com.soen390.conumap.calendar

import android.app.Activity
import android.content.Context
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import java.time.DayOfWeek
import java.text.SimpleDateFormat
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
            .setApplicationName("ConUMap")
            .build()
    }

    //Gets the events for the selected week
    fun getWeekEvents(minTime: DateTime, maxTime: DateTime): MutableList<Event>? {
/*
        val eventStrings = ArrayList<String>()

        val events = calendar!!.events().list("primary")
            .setTimeMin(minTime)//start range to look for events
            .setTimeMax(maxTime)//end range to look for events
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()

        val items = events.items

        for (event in items) {
            var start = event.start.dateTime.toString()
            var end = event.end.dateTime.toString()
            var location = event.location
            if (start == null) {
                start = event.start.date.toString()
            }
            else {
                var startStringArray = start.split("T").toTypedArray()
                var endStringArray = end.split("T").toTypedArray()
                start = startStringArray[0] + " at" + startStringArray[1].substring(0, 6)
                end = endStringArray[1].substring(0, 6)
            }
            eventStrings.add(String.format("%s|%s|%s|%s", event.summary, start, end, location))
            val sd = event.start.date.
        }

        return eventStrings*/
        val events = calendar!!.events().list("primary")
            .setTimeMin(minTime)//start range to look for events
            .setTimeMax(maxTime)//end range to look for events
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()



        return events.items

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

            var start = event.start.dateTime.toString()
            var end = event.end.dateTime.toString()
            var location = event.location
            if (start == null) {
                start = event.start.toString()
            }
            else {
                // Converts dates to local time then formats them so that only the HH:mm is left.
                val eventDateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
                val eventTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
                val tempStart = eventDateTimeFormatter.parse(start)
                val tempEnd = eventDateTimeFormatter.parse(end)
                start = eventTimeFormat.format(tempStart)
                end = eventTimeFormat.format(tempEnd)
            }
        nextEvent = String.format("%s|%s|%s|%s", event.summary, start, end, location)
        
        return nextEvent
    }



}