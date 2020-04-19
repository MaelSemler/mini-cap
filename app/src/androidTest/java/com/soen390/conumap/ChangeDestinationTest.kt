package com.soen390.conumap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.soen390.conumap.path.Path

import org.junit.Before
import org.junit.Test
import org.junit.After
import org.junit.runner.RunWith

import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)

class ChangeDestinationTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    //Change Destination System Test DEV-17
    @Test
    fun changeDestinationTest() {
        // Open app.
        device.pressRecentApps()

        sleep(2000)

        device.click(
            width / 2,
            height / 2
        )

        // Give time for app to load.
        sleep(7000)

        //Click on Directions Button
        val directionsButton: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/search_button"))
        directionsButton.click()
        sleep(1000) //Wait for application to load

        //Click on autocomplete search bar
        val autocompleteText: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_search_bar"))
        autocompleteText.waitForExists(2000)
        autocompleteText.setText("Hall"); //write keyword for a location
        device.pressBack() // To close keyboard
        sleep(1000) //wait for app to load

        //pick a prediction from those listed
        val prediction: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_prediction_primary_text"))
        prediction.click()
        sleep(4000) //wait for app to load

        //click on travel button
        val travelButton: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/travel_button"))
        travelButton.click()
        sleep(4000)

        val endingLocation: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/end_location_button"))
        endingLocation.click()


        val secondAutocompleteText: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_search_bar"))
        secondAutocompleteText.waitForExists(2000)
        assert(secondAutocompleteText.exists()) //assert the element specified exists
        secondAutocompleteText.setText("Montreal Airport"); //write keyword for a location
        assert(secondAutocompleteText.setText("Montreal Airport")) //asserts that the text box has been changed to "Hall"


        device.pressBack() // To close keyboard
        sleep(1000) //wait for app to load

        //pick a prediction from those listed
        val secondPrediction: UiObject =
            device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_prediction_primary_text"))
        secondPrediction.click()
        sleep(1000) //wait for app to load

        val directionsView: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/directions_popup"))
        directionsView.swipeUp(50)
        assert(directionsView.exists())
        assert(directionsView.contentDescription== Path._infoPathText.toString()) //asserts that the directions displayed are identical to what the path variable holds

        device.pressBack() // Ensure keyboard is closed for the following test.
    }
}
