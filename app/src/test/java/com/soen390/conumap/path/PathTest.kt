package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
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
    fun testSetOrigin() {
        val path = Path
        val mockOrigin = LatLng(0.1,0.2)
        whenever(mockPath.setOrigin(mockOrigin)).thenCallRealMethod()

        assertEquals(path.setOrigin(mockOrigin),mockPath.setOrigin(mockOrigin))
    }


    @Test
    fun setDestination() {
        val path = Path
        val mockDestination = LatLng(0.3,0.4)
        whenever(mockPath.setDestination(mockDestination)).thenCallRealMethod()

        assertEquals(path.setDestination(mockDestination),mockPath.setDestination(mockDestination))

    }

    @Test
    fun testFindDirections() {


    }


    @Test
    fun testGetAlternatives() {
        val path = Path
        val _alternateRouteId = MutableLiveData<Int>(0)

        assertEquals(path.getAlternatives(), _alternateRouteId.value!!.toInt())
    }

    @Test
    fun testUpdateSteps() {
        val path = Path

        path.updateSteps("Mocked Steps")

    }

    @Test
    fun testUpdateTotalDistance() {
        val path = Path
        val mockDistance = "Mocked Distance"

        verify(path.updateTotalDistance(mockDistance))


//        assertEquals(path.updateTotalDistance(mockDistance), mockPath.updateTotalDistance(mockDistance))
    }

    @Test
    fun testUpdateTotalDuration() {
    }

    @Test
    fun testUpdatePathInfo() {
    }


}