package com.soen390.conumap.calendar

import com.google.api.services.calendar.model.CalendarListEntry
import android.content.Context
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.soen390.conumap.event.Event

object Schedule {
    private var calendar: Calendar? = null
    private val calendarEntryList = mutableListOf<CalendarListEntry>()
    private var name: String? = null
    fun getName(): String? {
        return name
    }
    fun setName(name: String?) {
        this.name = name
    }

    //Sets up the google calendars
    fun setUpCalendar(con: Context): MutableList<String>? {
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val calendarNameList = mutableListOf<String>()
        val token = GoogleAuthUtil.getToken(con, name, "oauth2:https://www.googleapis.com/auth/calendar.readonly")
        var googleCred =  GoogleCredential()
        googleCred.accessToken = token
        calendar = Calendar.Builder(
            transport, jsonFactory, googleCred)
            .setApplicationName("ConUMap")
            .build()

        calendarEntryList.clear()
        val calendarList = calendar!!.calendarList().list().setPageToken(null).execute()
        val items = calendarList.items
        for (entry in items) {
            calendarEntryList.add(entry)
            calendarNameList.add(entry.summary)
        }
        return calendarNameList
    }

    //Gets the events for the selected week
    fun getWeekEvents(minTime: DateTime, maxTime: DateTime, index: Int): MutableList<Event>? {
        val eventList = mutableListOf<Event>()
        val events = calendar!!.events().list(calendarEntryList[index].id)
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

    //Gets the closed next event
    fun getNextEvent(index: Int):  Event {
        val now = DateTime(System.currentTimeMillis())
        val events = calendar!!.events().list(calendarEntryList[index].id)
            .setMaxResults(1)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val event = events.items.first()
        return Event(event)
    }
}

