package com.soen390.conumap.Directions


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DirectionsTest {

    var testDirectionObj = Directions()
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