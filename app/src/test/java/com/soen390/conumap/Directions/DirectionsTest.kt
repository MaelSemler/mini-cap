package com.soen390.conumap.Directions

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.gms.maps.GoogleMap
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.soen390.conumap.Directions.DataClass.directionsResponse
import com.soen390.conumap.map.Map
import org.json.JSONObject
import org.json.JSONTokener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.io.FileReader

class DirectionsTest {

    var gMap: GoogleMap = mock<GoogleMap>{}
    var activity: FragmentActivity = mock()

    var testDirectionObj = Directions()
    var map= mock <Map>()

//    mockDirections.updateTotalDuration("Mocked X min")
//    mockDirections.updatePathInfo("Mocked Path info")
//    mockDirections.updateTotalDistance("Mocked Total distance")
//    mockDirections.updateSteps("Mocked Steps")

    val mockedTotalDuration = "Mocked X min"
//    @Before
    fun setUp(){
        testDirectionObj.updateTotalDuration("Fake Duration")
        testDirectionObj.updatePathInfo("Fake Path info")
        testDirectionObj.updateTotalDistance("Fake Total distance")
        testDirectionObj.updateSteps("Fake Steps")
    }



    @Test
    fun testGetTotalDistanceText() {
        //Assert Equals
        testDirectionObj.updateTotalDistance("Fake Distance")
        assertEquals(testDirectionObj.getTotalDistanceText(), testDirectionObj._totalDistanceText)
        assertEquals(testDirectionObj.getTotalDistanceText(), "Fake Distance")

        //Assert Not Equals
        assertNotEquals(testDirectionObj.getTotalDistanceText(),"1234")

        //Set attributes to empty string
        testDirectionObj.updateTotalDistance("")
        assertEquals(testDirectionObj.getTotalDistanceText(),"")

    }

    @Test
    fun testGetTotalTimeText() {
        //Assert Equals
        testDirectionObj.updateTotalDuration("Fake Duration")
        assertEquals(testDirectionObj.getTotalTimeText(), testDirectionObj._totalTimeText)
        assertEquals(testDirectionObj.getTotalTimeText(), "Fake Duration")

        //Assert Not Equals
        assertNotEquals(testDirectionObj.getTotalTimeText(),"1234")

        //Set attributes to empty string
        testDirectionObj.updateTotalDistance("")
        assertEquals(testDirectionObj.getTotalDistanceText(),"")
    }

    @Test
    fun testGetInfoPathText() {
        //Assert Equals
        testDirectionObj.updatePathInfo("Fake Path Info")
        assertEquals(testDirectionObj.getInfoPathText(), testDirectionObj._infoPathText)
        assertEquals(testDirectionObj.getInfoPathText(), "Fake Path Info")

        //Assert Not Equals
        assertNotEquals(testDirectionObj.getInfoPathText(),"1234")

        //Set attributes to empty string
        testDirectionObj.updatePathInfo("")
        assertEquals(testDirectionObj.getInfoPathText(),"")

    }


    @Test
    fun testCleanUpDirections() {
        var textWithBracket = "<h>Hello</h>"

        assertEquals(testDirectionObj.cleanUpDirections(textWithBracket), "Hello")

        textWithBracket = "Hello"
        assertEquals(testDirectionObj.cleanUpDirections(textWithBracket), "Hello")

        textWithBracket = ""
        assertEquals(testDirectionObj.cleanUpDirections(textWithBracket), "")

        textWithBracket = "<bla>"
        assertEquals(testDirectionObj.cleanUpDirections(textWithBracket), "")

    }


}