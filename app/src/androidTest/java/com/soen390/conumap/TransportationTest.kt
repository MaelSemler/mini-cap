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

class TransportationTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    //Change Transportation System Test FT2-8
    @Test
    fun changeTransportationTest() {
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
        directionsButton.click()
        sleep(1000) //Wait for application to load

        //Click on autocomplete search bar
        val autocompleteText: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_search_bar"))
        autocompleteText.waitForExists(2000)
        autocompleteText.setText("Hall"); //write keyword for a location
        device.pressBack() // To close keyboard
        sleep(1000) //wait for app to load

        //pick a prediction from those listed
        val prediction: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/places_autocomplete_prediction_primary_text"))
        prediction.click()
        sleep(1000) //wait for app to load

        //click on travel button
        val travelButton: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/travel_button"))
        travelButton.click()
        sleep(1000)

        //Makes the directions visible
        val directionsView: UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/directions_popup"))
        directionsView.swipeUp(50)
        sleep(1000)//wait for app to load

        val directions:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/DirectionsTextBox"))
        directions.click()

        val transportation:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/transportation_walk"))
        transportation.click()
        assert(Path.transportationMode=="walking")
        directionsView.swipeUp(75)
        directions.click()
        assert(directions.contentDescription==Path._DescriptionText.toString())

        val transportation2:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/transportation_car"))
        transportation2.click()
        assert(Path.transportationMode=="driving")
        directionsView.swipeUp(75)
        directions.click()
        assert(directions.contentDescription==Path._DescriptionText.toString())

        val transportation3:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/transportation_bus"))
        transportation3.click()
        assert(Path.transportationMode=="transit")   //Assert path variable for transportation changed
        directionsView.swipeUp(75)

        directions.click()
        assert(directions.contentDescription==Path._DescriptionText.toString()) //Assert text matches what the path returns

        val transportation4:  UiObject=device.findObject(UiSelector().resourceId("com.soen390.conumap:id/transportation_bike"))
        transportation4.click()
        assert(Path.transportationMode=="bicycling")
        directionsView.swipeUp(100)

        directions.click()
        assert(directions.contentDescription==Path._DescriptionText.toString())

        device.pressBack() // Ensure keyboard is closed for the following test.
    }
}