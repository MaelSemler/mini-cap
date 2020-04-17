package com.soen390.conumap

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertEquals

import org.junit.Test
import org.junit.runner.RunWith

import java.lang.Thread.sleep
import java.util.*

@RunWith(AndroidJUnit4::class)
class CalendarFeaturesTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth
    //Your Google account must already be connected for this test to pass
    // SYSTEM TEST STX-X.
    fun reachCalendar(){
        // Open app.
        device.pressRecentApps()

        sleep(1000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(5000)

        val menuButton: UiObject = device.findObject(
            UiSelector().descriptionMatches("Open navigation drawer")
        )
        menuButton.click()

        val parentItem: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/design_navigation_view")
        )
        val calendarItem = parentItem.getChild(UiSelector().index(2))
        calendarItem.click()
        //Time for the calendar page to load
        sleep(5000)
    }
    @Test
    fun signInTest() {
        reachCalendar()
        val signOutButton: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/debug_sign_out")
        )
        //onView(withId(R.id.debug_sign_out)).check(matches(isClickable())) ///Change to this format
        assertEquals(signOutButton.isClickable, true)
    }

    @Test
    fun comingUpTest() {
        reachCalendar()
        val classNumberText: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/class_number_value")
        )
        assertEquals(classNumberText.text != "Class Number", true)

        val timeText: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/time_value")
        )
        assertEquals(timeText.text != "Time value", true)

        val roomText: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/room_location_value")
        )
        assertEquals(roomText.text != "Room Location value", true)

        val locationText: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/location_value")
        )
        assertEquals(locationText.text != "Location value", true)
    }

    @Test
    fun dropdownTest() {
        reachCalendar()
        val dropdown: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/dropdown_calendar")
        )
        //if the dropdown is initialised, then it is scrollable
        assertEquals(dropdown.isScrollable,true)
    }
    @Test
    fun calendarTest() {
        reachCalendar()
        val calendarText: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/month_year")
        )
        assertEquals(calendarText.text != "Month Year",true)

        val date = java.util.Calendar.getInstance()
        val month = date.getDisplayName(java.util.Calendar.MONTH,java.util.Calendar.LONG, Locale.getDefault())
        val year = date.get(java.util.Calendar.YEAR).toString()
        val dateStrings = calendarText.text.split(" ")
        //there are three possible date formats
        // Month Year, MonthA - MonthB Year, MonthA YearA - MonthB YearB
        when (dateStrings.size) {
            2 -> {
                assertEquals(dateStrings[0] == month,true)
                assertEquals(dateStrings[1] == year,true)
            }
            4 -> {
                assertEquals(dateStrings[0] == month || dateStrings[2] == month,true)
                assertEquals(dateStrings[3] == year,true)
            }
            5 -> {
                assertEquals(dateStrings[0] == month || dateStrings[3] == month,true)
                assertEquals(dateStrings[1] == year || dateStrings[4] == month,true)
            }
        }
    }
}