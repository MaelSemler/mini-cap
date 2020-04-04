package com.soen390.conumap

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.soen390.conumap.campus.Campus
import org.junit.Test

class CampusTests {
    var markerMock: Marker = mock()
    var gMap: GoogleMap = mock<GoogleMap> {
        on {addMarker(MarkerOptions())}.doReturn(markerMock)
    }

    @Test
    fun addCampusMarkerTest() {
        var testCampus = Campus("Test name", "Test info", LatLng(0.0, 0.0), gMap)
        testCampus.marker = markerMock
        testCampus.addCampusMarker(gMap)
    }
}