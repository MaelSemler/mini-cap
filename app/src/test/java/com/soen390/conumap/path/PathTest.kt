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

    }

    @Test
    fun testUpdateTotalDistance() {

    }

    @Test
    fun testUpdateTotalDuration() {
    }

    @Test
    fun testUpdatePathInfo() {
    }


}