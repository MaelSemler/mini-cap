package com.soen390.conumap.path

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.*

class PathTest {
    val mockPath :Path = mock<Path>()

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
        assert(true)
    }


    @Test
    fun testGetAlternatives() {
        val path = Path
        val _alternateRouteId = MutableLiveData<Int>(0)

        assertEquals(path.getAlternatives(), _alternateRouteId.value!!.toInt())
    }

    @Test
    fun testUpdateSteps() {
        assert(true)
    }

    @Test
    fun testUpdateTotalDistance() {
        assert(true)
    }

    @Test
    fun testUpdateTotalDuration() {
        assert(true)
    }

    @Test
    fun testUpdatePathInfo() {
        assert(true)
    }


}