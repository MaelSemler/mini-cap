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

import java.text.SimpleDateFormat
import java.util.*


object Schedule {
    var mCredential: GoogleAccountCredential? = null
    var calendar: Calendar? = null

    const val PREF_ACCOUNT_NAME = "accountName"//TODO: figure out if this is needed
    // I've seen it on most projects that use Google APIS so I think it's necessary

    fun initCredentials(activity: Activity) {
        mCredential = GoogleAccountCredential.usingOAuth2(
            activity.applicationContext,
            arrayListOf(CalendarScopes.CALENDAR))
            .setBackOff(ExponentialBackOff())
    }

    fun setUpCredentials(activity: Activity, name: String){
        if (name != null) {
            val settings = activity!!.getPreferences(Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(PREF_ACCOUNT_NAME, name)
            editor.apply()
            mCredential!!.selectedAccountName = name
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
        // I believe that when you set the calenderid to "primary", it only pulls from the primary calendar of whatever account you selected on sign in.
        // If we want users to be able to choose which calendar to pull from, then we'd need a way to pull the list of calendarids and display them to the user.
        val events = calendar!!.events().list("primary")//Todo: figure out what happens with multiple calendars to one account, does it only choose one? why? does it mix both?
            .setTimeMin(minTime)//start range to look for events
            .setTimeMax(maxTime)//end range to look for events
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        return events.items
    }

    fun getNextEvent():  com.soen390.conumap.event.Event { //Todo: change this to only return the next event DONE MAYBE
        val now = DateTime(System.currentTimeMillis())
        // Can't import Event class from ConUMaps since we've already imported the Google Event class. Also I think this is the only way since you need to get a list.
        val events = calendar!!.events().list("primary") //Todo: figure out here if there is an clean way to only get one event
            .setMaxResults(1)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items = events.items
        val event = items.first()

        var start = event.start.dateTime.toString()
        var end = event.end.dateTime.toString()
        if (start == null) {
            start = event.start.toString()
        }
        else {
            // Converts dates to local time then formats them so that only the HH:mm is left.
            val eventDateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
            val eventTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
            // Creates Date objects using local time.
            val tempStart = eventDateTimeFormatter.parse(start)
            val tempEnd = eventDateTimeFormatter.parse(end)
            start = eventTimeFormat.format(tempStart)
            end = eventTimeFormat.format(tempEnd)
        }

        var nextEvent = com.soen390.conumap.event.Event(event.summary, start, end, event.description, event.location)

        return nextEvent
    }



}