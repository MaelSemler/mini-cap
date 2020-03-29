package com.soen390.conumap.calendar


import com.google.api.services.calendar.model.CalendarListEntry

import android.app.Activity
import android.content.Context
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.soen390.conumap.event.Event

import java.text.SimpleDateFormat
import java.util.*


object Schedule {
    var mCredential: GoogleAccountCredential? = null
    var calendar: Calendar? = null
    val calendarEntryList = mutableListOf<CalendarListEntry>()

    const val PREF_ACCOUNT_NAME = "accountName"

    fun initCredentials(activity: Activity) {
        mCredential = GoogleAccountCredential.usingOAuth2(
            activity.applicationContext,
            arrayListOf(CalendarScopes.CALENDAR_READONLY))
            .setBackOff(ExponentialBackOff())
        /*
        mCredential = GoogleAccountCredential.usingOAuth2(
            activity.applicationContext,
            Collections.singleton(
                "https://www.googleapis.com/auth/calendar.readonly"))*/
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
    fun getWeekEvents(minTime: DateTime, maxTime: DateTime, calendarId: String): MutableList<Event>? {
        val eventList = mutableListOf<Event>()
        val events = calendar!!.events().list(calendarId)
            .setTimeMin(minTime)//start range to look for events
            .setTimeMax(maxTime)//end range to look for events
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        for(event in events.items){
            eventList.add(Event(event))
        }
        return eventList
    }

    fun getNextEvent(calendarId: String):  Event {
        val now = DateTime(System.currentTimeMillis())
        val events = calendar!!.events().list(calendarId)
            .setMaxResults(1)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val event = events.items.first()
        return Event(event)
    }

    fun getCalendarIDs():MutableList<String>? {

        val calendarIDList = mutableListOf<String>()
        val calendarList = calendar!!.calendarList().list().setPageToken(null).execute()
        val items = calendarList.items
        for (entry in items) {
            calendarEntryList.add(entry)
            calendarIDList.add(entry.summary)
        }
        return calendarIDList
    }

}