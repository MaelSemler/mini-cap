package com.soen390.conumap

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.hamcrest.Matchers.not

import org.junit.Test
import org.junit.runner.RunWith

import java.lang.Thread.sleep

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
        //Checking if the sign out button is on the view
        onView(withId(R.id.debug_sign_out)).check(matches(isClickable()))
    }

    @Test
    fun comingUpTest() {
        reachCalendar()
        //Checking that the next class info has been updated and is not set to default value
        onView(withId(R.id.class_number_value)).check(matches(withText(not("Class Number"))))
        onView(withId(R.id.time_value)).check(matches(withText(not("Time value"))))
        onView(withId(R.id.room_location_value)).check(matches(withText(not("Room Location value"))))
        onView(withId(R.id.location_value)).check(matches(withText(not("Location value"))))
    }

    @Test
    fun dropdownTest() {
        reachCalendar()
        //Checking that the drop down menu is initialized with calendars
        //The calendar is not focusable if no calendar is found
        onView(withId(R.id.dropdown_calendar)).check(matches(isFocusable()))
    }

    @Test
    fun calendarTest() {
        reachCalendar()
        //Checking that the month and year has been updated and is not set to default value on the calendar
        onView(withId(R.id.month_year)).check(matches(withText(not("Month Year"))))
    }
}