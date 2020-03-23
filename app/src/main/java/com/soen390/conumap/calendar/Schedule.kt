package com.soen390.conumap.calendar

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes

object Schedule {
    var mCredential: GoogleAccountCredential? = null
    var eventList: MutableList<String>? = null
    fun initSchedule (credential: GoogleAccountCredential){
       mCredential = credential
    }

}