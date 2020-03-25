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
import java.util.ArrayList

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

    /*
    * Gets the events and reu
    *
    * */

    fun getWeekEvents(weekCount: Int): ArrayList<String> {
        val minTime = findWeek(weekCount)
        val eventStrings = ArrayList<String>()

        val events = calendar!!.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(minTime)
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

    fun findWeek(weekCount: Int):DateTime{
        //get the
        return DateTime(System.currentTimeMillis())
    }

}