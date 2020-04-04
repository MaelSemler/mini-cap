package com.soen390.conumap


import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polygon
import com.nhaarman.mockitokotlin2.*
import com.soen390.conumap.building.BuildingInfoWindowAdapter
import com.soen390.conumap.helper.ContextPasser
import com.soen390.conumap.helper.DeviceLocationChecker
import com.soen390.conumap.map.Map
import org.junit.Test
import org.mockito.stubbing.Answer

class MapTests {

    var uisettingsMock: UiSettings = mock()
    var activity: FragmentActivity = mock()
    var mapStyleOptionsMock: MapStyleOptions = mock()
    var buildingInforAdapt: BuildingInfoWindowAdapter = mock()

    var gMap: GoogleMap = mock<GoogleMap>{
        on {uiSettings}.doReturn(uisettingsMock)
        on {setMapStyle(mapStyleOptionsMock)}.doAnswer(Answer { true })
        on {setInfoWindowAdapter(buildingInforAdapt)}.doAnswer( Answer {  })
    }

    var markerMock: Marker = mock()
    var polygonMock: Polygon = mock()


    var map: Map = mock()

    @Test
    fun getMapInstanceTest() {
        whenever(map.getMapInstance()).thenReturn(gMap)
        map.getMapInstance()
    }

    @Test
    fun setupMapTest(){
       //Map.setUpMap(gMap, activity)
    }

    @Test
    fun getCurrentLocationTest() {
        Map.getCurrentLocation()
    }

    @Test
    fun resetMapTest() {
        Map.resetMap()
    }

    @Test
    fun onMarkerClickTest() {
        Map.onMarkerClick(markerMock)
    }

    @Test
    fun onInfoWindowClickTest() {
        Map.onInfoWindowClick(markerMock)
    }

    @Test
    fun onPolygonClickTest() {
        Map.onPolygonClick(polygonMock)
    }

}
