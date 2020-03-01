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
class SwitchCampusTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    // SYSTEM TEST ST1-3.
    @Test
    fun moveCampusesTest() {
        // Open app.
        device.pressRecentApps()

        sleep(1000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(5000)

        // Zoom into map.
        val zoomInButton: UiObject = device.findObject(UiSelector().description("Zoom in"))
        zoomInButton.click()
        zoomInButton.click()
        zoomInButton.click()

        // Press SGW button.
        val sgwButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_SGW"))
        sgwButton.click()

        val sgwMarker: UiObject = device.findObject(UiSelector().description("SGW. "))
        sgwMarker.click()

        // Press LOY button.
        val loyButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_LOY"))
        loyButton.click()

        val loyMarker: UiObject = device.findObject(UiSelector().description("LOY. "))
        loyMarker.click()
    }
}
