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
class BuildingInformationTest {
    var device = UiDevice.getInstance(getInstrumentation())
    var height = device.displayHeight
    var width = device.displayWidth

    // SYSTEM TEST ST1-4.
    @Test
    fun showBuildingInformationTest() {
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
        for (i in 0..3) zoomInButton.click()

        // Open building info of each SGW building.
        val sgwButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_SGW"))
        sgwButton.click()
        val sgwHMarker: UiObject = device.findObject(UiSelector().description("Henry F. Hall Building. SGW Campus\n" +
                "1455 De Maisonneuve Blvd. W.."))
        sgwHMarker.click()
        val sgwLBMarker: UiObject = device.findObject(UiSelector().description("J.W. McConnel Building. SGW Campus\n" +
                "1400 De Maisonneuve Blvd. W.."))
        sgwLBMarker.click()
        val sgwLSMarker: UiObject = device.findObject(UiSelector().description("Learning Square. SGW Campus\n" +
                "1535 De Maisonneuve Blvd. W.."))
        sgwLSMarker.click()
        val sgwGMMarker: UiObject = device.findObject(UiSelector().description("Guy-De Maisonneuve Building. SGW Campus\n" +
                "1550 De Maisonneuve Blvd. W.."))
        sgwGMMarker.click()
        val sgwVAMarker: UiObject = device.findObject(UiSelector().description("Visual Arts Building. SGW Campus\n" +
                "1395 René Lévesque W.."))
        sgwVAMarker.click()
        val sgwEVMarker: UiObject = device.findObject(UiSelector().description("Engineering, Computer Science and Visual Arts Integrated Complex. SGW Campus\n" +
                "1515 St. Catherine W.."))
        sgwEVMarker.click()
        val sgwMBMarker: UiObject = device.findObject(UiSelector().description("John Molson Building. SGW Campus\n" +
                "1450 Guy."))
        sgwMBMarker.click()
        val sgwFBMarker: UiObject = device.findObject(UiSelector().description("Faubourg Building. SGW Campus\n" +
                "1250 Guy."))
        sgwFBMarker.click()
        val sgwFGMarker: UiObject = device.findObject(UiSelector().description("Faubourg Ste-Catherine Building. SGW Campus\n" +
                "1610 St. Catherine W.."))
        sgwFGMarker.click()
        val sgwGNMarker: UiObject = device.findObject(UiSelector().description("Grey Nuns Building. SGW Campus\n" +
                "1190 Guy."))
        sgwGNMarker.click()

        // Open building info of each LOY building.
        val loyButton: UiObject = device.findObject(UiSelector().resourceId("com.soen390.conumap:id/button_LOY"))
        loyButton.click()
        val loyPSMarker: UiObject = device.findObject(UiSelector().description("Physical Services Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyPSMarker.click()
        val loyHCMarker: UiObject = device.findObject(UiSelector().description("Hingston Hall, wing HC. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyHCMarker.click()
        val loyHAMarker: UiObject = device.findObject(UiSelector().description("Hingston Hall, wing HA. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyHAMarker.click()
        val loyPTMarker: UiObject = device.findObject(UiSelector().description("Oscar Peterson Concert Hall. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyPTMarker.click()
        val loySCMarker: UiObject = device.findObject(UiSelector().description("Student Centre. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loySCMarker.click()
        val loyHBMarker: UiObject = device.findObject(UiSelector().description("Hingston Hall, wing HB. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyHBMarker.click()
        val loyPYMarker: UiObject = device.findObject(UiSelector().description("Psychology Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyPYMarker.click()
        val loyVLMarker: UiObject = device.findObject(UiSelector().description("Vanier Library Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyVLMarker.click()
        val loyFCMarker: UiObject = device.findObject(UiSelector().description("F. C. Smith Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyFCMarker.click()
        val loyCCMarker: UiObject = device.findObject(UiSelector().description("Central Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyCCMarker.click()
        val loyADMarker: UiObject = device.findObject(UiSelector().description("Administration Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyADMarker.click()
        val loySPMarker: UiObject = device.findObject(UiSelector().description("Richard J. Renaud Science Complex. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loySPMarker.click()
        val loyCJMarker: UiObject = device.findObject(UiSelector().description("Communication Studies and Journalism Building. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyCJMarker.click()
        val loyGEMarker: UiObject = device.findObject(UiSelector().description("Centre for Structural and Functional Genomics. Loyola Campus\n" +
                "7141 Sherbrooke W.."))
        loyGEMarker.click()
    }
}
