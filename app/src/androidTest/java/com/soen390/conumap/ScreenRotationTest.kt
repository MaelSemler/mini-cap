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
class ScreenRotationTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    // SYSTEM TEST ST1-7.
    @Test
    fun displayUIInBothOrientationsTest() {
        // Open app.
        device.pressRecentApps()

        sleep(1000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(5000)

        device.setOrientationLeft()
        sleep(1000)
        pressButtons()
        device.setOrientationNatural()
        sleep(1000)
        pressButtons()
        device.setOrientationRight()
        sleep(1000)
        pressButtons()
        device.setOrientationNatural()
    }

    // Presses all present UI buttons to make sure they work in both orientations.
    fun pressButtons() {
        // Press SGW button.
        val sgwButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_SGW"))
        sgwButton.click()

        // Press LOY button.
        val loyButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_LOY"))
        loyButton.click()

        // Press Zoom In button.
        val zoomInButton: UiObject = device.findObject(UiSelector().description("Zoom in"))
        zoomInButton.click()

        // Press Zoom Out button.
        val zoomOutButton: UiObject = device.findObject(UiSelector().description("Zoom out"))
        zoomOutButton.click()

        val locateUserButton: UiObject = device.findObject(UiSelector().description("My Location"))
        locateUserButton.click()
    }
}
