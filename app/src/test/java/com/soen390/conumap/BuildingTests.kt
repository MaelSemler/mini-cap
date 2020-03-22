package com.soen390.conumap

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.soen390.conumap.building.BuildingCreator
import com.soen390.conumap.permission.Permission
import org.junit.Assert
import org.junit.Test
import java.util.ArrayList

class BuildingTests {


    var googleMapMarkerOptionsMock: MarkerOptions = MarkerOptions()
        .position(LatLng(45.458275, -73.640469))
        .alpha(0.0F)
        .title("test")
        .snippet("info")


    var gMap: GoogleMap = mock<GoogleMap> {
        //on {addMarker(MarkerOptions())}.doReturn(googleMapMarkerOptionsMock)
    }

    var mockArrayOfString: Array<String> = arrayOf("40.0", "50.0")
    var resources: Resources = mock<Resources> {
        on {getStringArray(R.array.buildingHPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwHTarget)}.doReturn(mockArrayOfString)
    }

    var context: Context = mock<Context> {
        on { getString(R.string.sgwHName) }.doReturn("test")
        on { getString(R.string.sgwHInfo) }.doReturn("test")
        on { resources }.doReturn(resources)
    }

    var creatBuildingsss: BuildingCreator = mock()


    @Test
    fun buildingCreatorTests() {
        BuildingCreator.setContext(context)
        BuildingCreator.createBuildings(gMap)
    }


}