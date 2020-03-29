package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.nhaarman.mockitokotlin2.*
import com.soen390.conumap.Directions.Directions
import com.soen390.conumap.map.Map
import org.junit.Assert.*

class PathTest {
    val mockPath :Path = mock<Path>()
    var gMap: GoogleMap = mock<GoogleMap>{}
    var uisettingsMock: UiSettings = mock()
    var activity: FragmentActivity = mock()
    var map= mock<Map>()

//    @Before
//    fun setUp() {
//
//        mockPath._directionText.value = "1.First instructions \n 2.Second instruction \n 3. Destination"
//        mockPath._infoPathText.value = "Via infoPathText"
//        mockPath._totalDistanceText.value = "Total Distance is X"
//        mockPath._totalTimeText.value = "Total duration of the route is Y"
//        mockPath.dirObj = mock<Directions>()
//        mockPath.dirObj.directionArray.addAll(listOf("1.Walk straight", "\n 2. Turn right ", "\n 3. Turn left ", "\n 4. Arrived at Destination"))
//        mockPath.dirObj.distanceArray.addAll(listOf("Distance 1 ", "Distance 2", "Distance 3"))
//        mockPath.dirObj.durationArray.addAll(listOf("Duration 1", "Duration 2","Duration 3"))
//        mockPath.directionsArray.add(mockPath.dirObj)
//
//
//    }
//
//    @After
//    fun tearDown() {
//    }

    @Test
    fun get_directionText() {
        assertEquals(mockPath._directionText, mockPath.directionText)
    }

    @Test
    fun get_totalDistanceText() {
        assertEquals(mockPath._totalDistanceText, mockPath.totalDistanceText)
    }

    @Test
    fun get_totalTimeText() {
        assertEquals(mockPath._totalTimeText, mockPath.totalTimeText)
    }

    @Test
    fun get_infoPathText() {
        assertEquals(mockPath._infoPathText, mockPath.infoPathText)
    }

    @Test
    fun getDirectionsArray() {
        assertTrue(mockPath.directionsArray.size == 1)
        assertTrue(mockPath.directionsArray[0] == mockPath.dirObj)
    }

    @Test
    fun setDirectionsArray() {

//        asset
    }

    @Test
    fun getDirectionText() {
    }

    @Test
    fun getTotalDistanceText() {
    }

    @Test
    fun getTotalTimeText() {
    }

    @Test
    fun getInfoPathText() {
    }

    @Test
    fun getMap() {
    }

    @Test
    fun getDirObj() {
    }

    @Test
    fun setDirObj() {
    }

    @Test
    fun setOrigin() {
    }

    @Test
    fun setDestination() {
    }

    @Test
    fun findDirections() {
    }

    @Test
    fun switchOriginAndDestination() {
    }

    @Test
    fun getAlternatives() {
    }

    @Test
    fun updateSteps() {
    }

    @Test
    fun updateTotalDistance() {
    }

    @Test
    fun updateTotalDuration() {
    }

    @Test
    fun updatePathInfo() {
    }


