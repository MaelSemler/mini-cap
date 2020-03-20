package com.soen390.conumap

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.soen390.conumap.map.Map
import com.soen390.conumap.map.MapFragment
import com.soen390.conumap.permission.Permission
import org.junit.Assert
import org.junit.Test

class MapTests {

    var gMap: GoogleMap = mock()
    var activity: FragmentActivity = mock()
    var map: Map = mock()

    @Test
    fun setupMapTest() {
        whenever(map.getMapInstance()).thenReturn(gMap)
        map.getMapInstance()

        //map.setUpMap(gMap, activity)
    }

    /*@Test
    fun mapFragmentTest() {
        val fragmentArgs = Bundle().apply {
            putInt("selectedListItem", 0)
        }

        val factory = FragmentFactory()
        val scenario = launchFragmentInContainer<MapFragment>()
    }*/

    @Test
    fun getCurrentLocation() {
        Map.getCurrentLocation()
    }
}