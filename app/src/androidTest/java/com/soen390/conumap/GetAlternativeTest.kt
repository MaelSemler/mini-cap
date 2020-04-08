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

class GetAlternativeTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    //Get Alternative System Test FT2-9
    @Test
    fun getAlternativeTest() {
        // Open app.
        device.pressRecentApps()

        sleep(2000)

        device.click(
            width / 4,
            height / 4
        )

        // Give time for app to load.
        sleep(7000)

        //Click on Directions Button
        val directionsButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/search_button"))
        assert(directionsButton.exists()) //Assert is true if the button actually exists
        assert(directionsButton.isClickable) //Assert is true if the directionsButton is clickable
        directionsButton.click()

        sleep(1000) //Wait for application to load

        //Click on autocomplete search bar
        val autocompleteText: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_search_bar"))
        autocompleteText.waitForExists(2000)
        assert(autocompleteText.exists()) //assert the element specified exists
        autocompleteText.setText("Hall"); //write keyword for a location
        assert(autocompleteText.setText("Hall")) //asserts that the text box has been changed to "Hall"

        //pick a prediction from those listed
        val prediction: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_prediction_primary_text"))
        assert(prediction.exists())
        val predictionText= prediction.text //variable containing the text of the prediction. Will be used to assert later on
        prediction.click()
        sleep(1000) //wait for app to load

        val result: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/found_location_button"))
        assert(result.exists())
        assert(result.text==predictionText) //assert that the text displayed to user in text box is identical to the text of the prediction they clicked.

        sleep(1000) //wait for app to load

        assert(!prediction.exists()) //assert that the prediction element no longer exists


        //device.pressBack() // To close keyboard. This will fail if a keyboard is already minimized and will cause it to exist the app
        //assert(device.pressBack()) //assert that the device did indeed go back to close keyboard
        //sleep(1000) //wait for app to load


        //click on travel button
        val travelButton: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/travel_button"))
        assert(travelButton.exists())
        travelButton.click()

        sleep(1000)

        assert(!travelButton.exists())
        val directions:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/DirectionsTextBox"))
        assert(directions.exists())
        assert(directions.contentDescription== Path._infoPathText.toString()) //asserts that the directions displayed are identical to what the path variable holds

        //device.pressBack() // Will FAIL test if keyboard is already minimized

        //tests alternative
        val alternative:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/alt_button"))
        assert(alternative.exists())
        alternative.click()
        assert(directions.contentDescription==Path._PathAlternateText.toString())

        //tests alternative after changing mode of transportation
        val transportation:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/transportation_walk"))
        transportation.click()
        assert(Path.transportationMode=="walking")
        alternative.click()
        assert(directions.exists())
        directions.click()


        device.pressBack() // Ensure keyboard is closed for the following test.
    }
}