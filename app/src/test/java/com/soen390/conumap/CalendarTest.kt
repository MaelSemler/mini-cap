package com.soen390.conumap

import com.soen390.conumap.calendar.Schedule
import org.junit.Assert.assertEquals
import org.junit.Test

class CalendarTest {
    @Test
    fun scheduleTest() {
        Schedule.setName("test")
        assertEquals("test",Schedule.getName())
    }
}
