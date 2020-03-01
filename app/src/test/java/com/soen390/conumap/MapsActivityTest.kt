package com.soen390.conumap

import org.junit.Test
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import org.mockito.Mock
import java.io.File
import kotlin.reflect.typeOf
import com.soen390.conumap.MapsActivity as MapsActivity
import org.junit.Before as Before


class MapsActivityTest {

    private val activity = MapsActivity()



    @Test
    fun originDestination_isCorrect(){
        val currentLoc = LatLng(45.502516, -73.563929)
        val incorrectLoc = LatLng(45.6, -73.59)

        fun location_isCorrect(locLatLng: LatLng): Boolean {
            val realLocLatLng = LatLng(45.502516, -73.563929)

            assertNotNull(locLatLng)

            return (locLatLng.latitude == realLocLatLng.latitude && locLatLng.longitude ==realLocLatLng.longitude)
        }

        assertTrue(location_isCorrect(currentLoc))
        assertFalse(location_isCorrect(incorrectLoc))

    }

    @Test
    fun checkInput(){
        fun search_isNull(input:String):Boolean{
            return (input==null)
        }
        val nullString=null
        val emptyString=""
        val aString="a"

        assertTrue(search_isNull(nullString.toString()))
        assertFalse(search_isNull(aString))
        assertTrue(search_isNull(emptyString))

    }

    //Mock does not want to work
    @Mock
    val jsonResponse :String = File("/responseTest.json").toString()

    @Test
    fun extractDirections_isCorrect(){

        val jsonObj = JSONObject(jsonResponse)
        val routes = jsonObj.getJSONArray("routes")
        val legs = routes.getJSONObject(0).getJSONArray("legs")
        val steps = legs.getJSONObject(0).getJSONArray("steps")

        assertEquals("Direction: '\n' 1. " +
                "2. Turn <b>left</b> onto <b>Boulevard René-Lévesque O S</b>'\n'" +
                "3. Turn <b>right</b> onto <b>Rue Bishop</b>'\n'" +
                "4. Turn <b>left</b> onto <b>Boulevard de Maisonneuve O</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>", activity.extractDirections(steps))

    }
}
