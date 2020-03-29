package com.soen390.conumap.event

import java.text.SimpleDateFormat
import java.util.*

class Event (private val googleEvent: com.google.api.services.calendar.model.Event) {

    companion object{
        private val eventDateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        private val eventTimeFormat = SimpleDateFormat("HH:mm", Locale.US)
    }
    var eventName: String? = googleEvent.summary
    var start: Long? = googleEvent.start.dateTime.value
    var end: Long? = googleEvent.end.dateTime.value
    var duration: Long? = end!! - start!!
    var startTime: String? = eventTimeFormat.format(eventDateTimeFormatter.parse(googleEvent.start.dateTime.toString()))
    var endTime: String? = eventTimeFormat.format(eventDateTimeFormatter.parse(googleEvent.end.dateTime.toString()))
    var roomNumber: String? = googleEvent.description
    var buildingLocation: String? = googleEvent.location

}