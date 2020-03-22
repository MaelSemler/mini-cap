package com.soen390.conumap

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.MapStyleOptions
import com.nhaarman.mockitokotlin2.*
import com.soen390.conumap.map.Map
import com.soen390.conumap.map.MapFragment
import com.soen390.conumap.permission.Permission
import org.junit.Assert
import org.junit.Test
import org.mockito.stubbing.Answer


class MapTests {

    var uisettingsMock: UiSettings = mock()
    var activity: FragmentActivity = mock()
    var mapStyleOptionsMock: MapStyleOptions = mock()

    var gMap: GoogleMap = mock<GoogleMap>{
        on {uiSettings}.doReturn(uisettingsMock)
        on {setMapStyle(mapStyleOptionsMock)}.doAnswer(Answer { true })
    }

    var map: Map = mock()

    @Test
    fun getMapInstanceTest() {
        whenever(map.getMapInstance()).thenReturn(gMap)
        map.getMapInstance()

        //map.setUpMap(gMap, activity)
    }

    @Test
    fun setupMapTest(){
       //Map.setUpMap(gMap, activity)
    }

    @Test
    fun mapFragmentTest() {
       /* val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }

        val factory = FragmentFactory()
        val scenario = launchFragmentInContainer<MapFragment>()*/

       // FragmentScenario.launchInContainer(MapFragment::class.java)

    }

    @Test
    fun getCurrentLocation() {
        Map.getCurrentLocation()
    }
}
