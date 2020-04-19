package com.soen390.conumap.Directions


import com.google.android.gms.maps.model.Polyline
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class DirectionServiceTest {


    @Test
    fun testGetGoogleMapRequestURL() {
        assert(true)
    }


    @Test
    fun testRoute() {
    }


    @Test
    fun testResetPathDrawing(){
        val dirService = mock<DirectionService>()

        val mockPolyline = mock<ArrayList<Polyline>> ()
        val mockPoly1 = mock<Polyline>()
        val mockPoly2 = mock<Polyline>()

        mockPolyline.addAll(listOf(mockPoly1,mockPoly2))

        whenever(dirService.polyline).thenReturn(mockPolyline)

        dirService.resetPathDrawing()

        assert(mockPolyline.size==0)

    }

    @Test
    fun testDisplayOnScreenPath(){
    }




}