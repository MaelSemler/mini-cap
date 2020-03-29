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
import com.soen390.conumap.building.Building
import com.soen390.conumap.building.BuildingCreator
import com.soen390.conumap.permission.Permission
import org.junit.Assert
import org.junit.Test
import java.util.ArrayList

class BuildingTests {

    var markerMock: Marker = mock()


    var gMap: GoogleMap = mock<GoogleMap> {
      on {addMarker(MarkerOptions())}.doReturn(markerMock)
    }

    var mockArrayOfString: Array<String> = arrayOf("40.0", "50.0")

    //Mock the resrouces so that it does not crash
    var resources: Resources = mock<Resources> {
        on {getStringArray(R.array.buildingHPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwHTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingGMPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwGMTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingMBPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwMBTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingEVPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwEVTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingFGPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwFGTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingFBPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwFBTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingLBPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwLBTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingGNPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwGNTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingLSPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwLSTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingVAPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.sgwVATarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingGEPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyGETarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingCJPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyCJTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingADPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyADTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingSPPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loySPTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingCCPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyCCTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingFCPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyFCTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingVLPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyVLTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingVLPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loySCTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingVLPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyPTTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingPSPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyPSTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingPYPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyPYTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingRFPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyRFTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingHAPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyHATarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingHBPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyHBTarget)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.buildingHCPoints)}.doReturn(mockArrayOfString)
        on {getStringArray(R.array.loyHCTarget)}.doReturn(mockArrayOfString)
    }

    //mock all the building values so that it does not crash
    var context: Context = mock<Context> {
        on { getString(R.string.sgwHName) }.doReturn("test")
        on { getString(R.string.sgwHInfo) }.doReturn("test")
        on { getString(R.string.sgwGMName) }.doReturn("test")
        on { getString(R.string.sgwGMInfo) }.doReturn("test")
        on { getString(R.string.sgwMBName) }.doReturn("test")
        on { getString(R.string.sgwMBInfo) }.doReturn("test")
        on { getString(R.string.sgwEVName) }.doReturn("test")
        on { getString(R.string.sgwEVInfo) }.doReturn("test")
        on { getString(R.string.sgwFGName) }.doReturn("test")
        on { getString(R.string.sgwFGInfo) }.doReturn("test")
        on { getString(R.string.sgwFBName) }.doReturn("test")
        on { getString(R.string.sgwFBInfo) }.doReturn("test")
        on { getString(R.string.sgwLBName) }.doReturn("test")
        on { getString(R.string.sgwLBInfo) }.doReturn("test")
        on { getString(R.string.sgwGNName) }.doReturn("test")
        on { getString(R.string.sgwGNInfo) }.doReturn("test")
        on { getString(R.string.sgwLSName) }.doReturn("test")
        on { getString(R.string.sgwLSInfo) }.doReturn("test")
        on { getString(R.string.sgwVAName) }.doReturn("test")
        on { getString(R.string.sgwVAInfo) }.doReturn("test")
        on { getString(R.string.loyGEName) }.doReturn("test")
        on { getString(R.string.loyGEInfo) }.doReturn("test")
        on { getString(R.string.loyCJName) }.doReturn("test")
        on { getString(R.string.loyCJInfo) }.doReturn("test")
        on { getString(R.string.loyADName) }.doReturn("test")
        on { getString(R.string.loyADInfo) }.doReturn("test")
        on { getString(R.string.loySPName) }.doReturn("test")
        on { getString(R.string.loySPInfo) }.doReturn("test")
        on { getString(R.string.loyCCName) }.doReturn("test")
        on { getString(R.string.loyCCInfo) }.doReturn("test")
        on { getString(R.string.loyFCName) }.doReturn("test")
        on { getString(R.string.loyFCInfo) }.doReturn("test")
        on { getString(R.string.loyVLName) }.doReturn("test")
        on { getString(R.string.loyVLInfo) }.doReturn("test")
        on { getString(R.string.loySCName) }.doReturn("test")
        on { getString(R.string.loySCInfo) }.doReturn("test")
        on { getString(R.string.loyPTName) }.doReturn("test")
        on { getString(R.string.loyPTInfo) }.doReturn("test")
        on { getString(R.string.loyPSName) }.doReturn("test")
        on { getString(R.string.loyPSInfo) }.doReturn("test")
        on { getString(R.string.loyPYName) }.doReturn("test")
        on { getString(R.string.loyPYInfo) }.doReturn("test")
        on { getString(R.string.loyRFName) }.doReturn("test")
        on { getString(R.string.loyRFInfo) }.doReturn("test")
        on { getString(R.string.loyHAName) }.doReturn("test")
        on { getString(R.string.loyHAInfo) }.doReturn("test")
        on { getString(R.string.loyHBName) }.doReturn("test")
        on { getString(R.string.loyHBInfo) }.doReturn("test")
        on { getString(R.string.loyHCName) }.doReturn("test")
        on { getString(R.string.loyHCInfo) }.doReturn("test")
        on { resources }.doReturn(resources)
    }


    //Verify if the BUildingCreator singleton works
    @Test
    fun buildingCreatorTests() {
        BuildingCreator.setContext(context)
        BuildingCreator.createBuildings(gMap)
    }


}