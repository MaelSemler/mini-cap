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
class LocateUserTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    // SYSTEM TEST ST1-1.
    @Test
    fun pressLocateUserButtonTest() {
        // Open app.
        device.pressRecentApps()

        sleep(1000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(5000)

        // Move map so it is not centered on user.
        device.drag(
            (width * 0.25).toInt(),
            height / 2,
            (width * 0.75).toInt(),
            height / 2,
            100
        )

        // Press locate user button.
        val locateUserButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_current_location"))
        locateUserButton.click()

        // Give time for app to load.
        sleep(1000)
    }
}