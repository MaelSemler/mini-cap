package com.soen390.conumap.building

import android.graphics.Color
import android.graphics.Color.rgb
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Building(var name: String, var info: String, var location: LatLng, var outlineArray: Array<String>,
               var touchTargetArray: Array<String>, map: GoogleMap, var touchTargetID: Float) {
    var marker: Marker
    var outline: PolygonOptions
    var touchTarget: PolygonOptions

    init {
        this.marker = addBuildingMarker(map)
        this.outline = addBuildingOutline(map)
        this.touchTarget = addBuildingTouchTarget(map)
    }

    // Adds marker for this building to the map passed as an argument.
    private fun addBuildingMarker(map: GoogleMap): Marker {
        return map.addMarker(MarkerOptions()
            .position(location)
            .alpha(0.0F)
            .title(name)
            .snippet(info)
        )
    }

    // Add the precise visual outline of the building.
    private fun addBuildingOutline(map: GoogleMap): PolygonOptions {
        val outline = PolygonOptions()
        for (i in outlineArray.indices) {
            if (i % 2 == 0) {
                outline.add(LatLng(outlineArray[i].toDouble(), outlineArray[i + 1].toDouble()))
            }
        }
        map.addPolygon(outline
            .fillColor(rgb(147, 35, 57))
            .strokeColor(Color.argb(0, 0, 0, 0))
        )
        return outline
    }

    // Add the touch target, which will display building information when tapped.
    private fun addBuildingTouchTarget(map: GoogleMap): PolygonOptions {
        val target = PolygonOptions()
        for(i in touchTargetArray.indices) {
            if(i % 2 == 0) {
                target.add(LatLng(touchTargetArray[i].toDouble(), touchTargetArray[i + 1].toDouble()))
            }
        }
        map.addPolygon(target
            .fillColor(Color.argb(0, 0, 0, 0))
            .strokeColor(Color.argb(0, 0, 0, 0))
            .clickable(true)
            .zIndex(touchTargetID)
        )
        return target
    }
}
