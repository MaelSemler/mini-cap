package com.soen390.conumap.building

import android.graphics.Color
import android.graphics.Color.rgb
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Building(var name: String, var info: String, var location: LatLng, map: GoogleMap,
               var polygonID: Float, var coordinateArray: Array<String>) {
    var marker: Marker
    var polygon: PolygonOptions

    init {
        this.marker = addBuildingMarker(map)
        this.polygon = addBuildingOutline(map)
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

    // Add the visual outline of the building.
    private fun addBuildingOutline(map: GoogleMap): PolygonOptions {
        val outline = PolygonOptions()
        for (i in coordinateArray.indices) {
            if (i % 2 == 0) {
                outline.add(LatLng(coordinateArray[i].toDouble(), coordinateArray[i + 1].toDouble()))
            }
        }
        map.addPolygon(outline
            .fillColor(rgb(147, 35, 57))
            .strokeColor(Color.argb(0, 0, 0, 0))
        )
        return outline
    }

    // Add the touch target, which will display building information when tapped.
}
