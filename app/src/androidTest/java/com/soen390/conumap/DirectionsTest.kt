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

class DirectionsTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    //Directions System Test ST2-8
    @Test
    fun directionsTest()
    {
        // Open app.
        device.pressRecentApps()

        sleep(1000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(5000)

        //Click on Directions Button

        val directionsButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/search_button"))
        directionsButton.click()
        sleep(1000)
        val autocompleteText: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_search_bar"))
        autocompleteText.waitForExists(2000)
        autocompleteText.setText("Hall");
        val prediction: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_prediction_primary_text"))
        prediction.waitForExists(2000)
        val travelButton: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/travel_button"))
        travelButton.click()

        sleep(1000)


    }


}