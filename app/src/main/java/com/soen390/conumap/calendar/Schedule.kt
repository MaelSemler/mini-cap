package com.soen390.conumap.calendar

import com.google.api.services.calendar.model.CalendarListEntry
import android.app.Activity
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.soen390.conumap.event.Event

object Schedule {
    private var credential: GoogleAccountCredential? = null
    private var calendar: Calendar? = null
    private val calendarEntryList = mutableListOf<CalendarListEntry>()
    private const val PREF_ACCOUNT_NAME = "accountName"
    private var name = ""
   // private var googelCred: GoogleCredential? = null

    //initiates the credentials with the scope of the calendars
    fun initCredentials(activity: Activity) {
        credential = GoogleAccountCredential.usingOAuth2(
            activity.applicationContext,
            arrayListOf(CalendarScopes.CALENDAR))
            .setBackOff(ExponentialBackOff())
    }

    //Sets up the credentials with an account
    fun getName (email: String){
        name = email
    }
    fun setUpCredentials(con: Context){

        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val token = GoogleAuthUtil.getToken(con, name, "oauth2:https://www.googleapis.com/auth/calendar.readonly")
        var googleCred =  GoogleCredential.Builder().setTransport(NetHttpTransport()).setJsonFactory(jsonFactory)
            .build()

        googleCred.accessToken = token
        calendar = Calendar.Builder(
            transport, jsonFactory, googleCred)
            .setApplicationName("ConUMap")
            .build()

    }

    //Sets up the google calendar
    fun setUpCalendar(){
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        calendar = Calendar.Builder(
            transport, jsonFactory, credential)
            .setApplicationName("ConUMap")
            .build()
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

    //Resets the calendarEntryList and Gets the different google calendars
    fun getCalendarIDs():MutableList<String>? {
        calendarEntryList.clear()
        val calendarNameList = mutableListOf<String>()
        val calendarList = calendar!!.calendarList().list().setPageToken(null).execute()
        val items = calendarList.items
        for (entry in items) {
            calendarEntryList.add(entry)
            calendarNameList.add(entry.summary)
        }
        return calendarNameList
    }



}

