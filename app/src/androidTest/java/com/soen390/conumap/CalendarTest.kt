package com.soen390.conumap

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector

import org.junit.Before
import org.junit.Test
import org.junit.After
import org.junit.runner.RunWith

import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class CalendarTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth
/*
    // SYSTEM TEST STX-X.
    @Test
    fun signInTest() {
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

        val signInButton: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/sign_in_button")
        )
        signInButton.click()


        //Have some sort of confirmation that the "Choose an account" window pops up
        /*
        * if sign in button found, stop test
        * if not,
        * see that */
    }

    @Test
    fun signedInTest() {
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

        //assert() the sign in button is there
        //if it's there end all tests
        //if not assert that the go to button is there
        val signInButton: UiObject = device.findObject(
            UiSelector().resourceId("com.soen390.conumap:id/sign_in_button")
        )
        signInButton.click()


        //Have some sort of confirmation that the "Choose an account" window pops up
        /*
        * if sign in button found, stop test
        * if not,
        * see that */
    }*/


}