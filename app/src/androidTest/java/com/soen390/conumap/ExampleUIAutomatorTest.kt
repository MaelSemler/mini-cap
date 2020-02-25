package com.soen390.conumap

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice

import org.junit.Before
import org.junit.Test
import org.junit.After
import org.junit.runner.RunWith

import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ExampleUIAutomatorTest {
    var device = UiDevice.getInstance(getInstrumentation())

    // Place pauses between actions so you can see them performed on the phone.
    // Set false to run faster but you won't be able to see individual interactions.
    var debug = true

    @Test
    // Very temporary solution.
    // This only works because I happen to have an app icon where it is clicking.
    // We need to find a better way to do this (open from app drawer maybe?)
    fun openApp() {
        device.pressHome()
        device.click(device.displayWidth / 2, device.displayHeight / 2)
    }

    @Test
    // Again, temporary. May not work on devices with different resolutions.
    fun moveMapAround() {
        if(debug) sleep(1000)

        // Drag left.
        device.drag(
            1200,
            2000,
            200,
            2000,
            100
        )

        if(debug) sleep(1000)

        // Drag right.
        device.drag(
            200,
            2000,
            1200,
            2000,
            100
        )

        if(debug) sleep(1000)

        // Drag up.
        device.drag(
            700,
            2000,
            700,
            1000,
            100
        )

        if(debug) sleep(1000)

        // Drag down.
        device.drag(
            700,
            1000,
            700,
            2000,
            100
        )

        if(debug) sleep(1000)

        // Clicks on marker on map. Need to figure out how to click on certain elements and not
        // hardcoded coordinates.
        device.click(715, 1400)

        if(debug) sleep(1000)
    }
}