package com.soen390.conumap

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.soen390.conumap.building.BuildingCreator
import com.soen390.conumap.permission.Permission
import org.junit.Assert
import org.junit.Test
import java.util.ArrayList

class BuildingTests {

    var gMap: GoogleMap = mock()
    var mockArrayOfString: Array<String> = arrayOf("40.0", "50.0")
    var context: Context = mock<Context> {
        on { getString(R.string.sgwHName) }.doReturn("test")
        on { getString(R.string.sgwHInfo) }.doReturn("test")
        on { resources.getStringArray(R.array.buildingHPoints) }.doReturn(mockArrayOfString)
    }

    var creatBuildingsss: BuildingCreator = mock()


    @Test
    fun buildingCreatorTests() {
        BuildingCreator.setContext(context)
        BuildingCreator.createBuildings(gMap)
    }


}