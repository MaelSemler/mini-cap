package com.soen390.conumap.building

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Building(var name: String, var info: String, var location: LatLng, map: GoogleMap) {
    var marker: Marker
    var touchTarget: Polygon

    init {
        this.marker = addBuildingMarker(map)
        this.touchTarget = addTouchTarget(map)
    }

    // Adds marker for this building to the map passed as an argument.
    fun addBuildingMarker(map: GoogleMap): Marker {
        return map.addMarker(MarkerOptions()
            .position(location)
            .alpha(0.0F)
            .title(name)
            .snippet(info)
        )
    }

    fun addTouchTarget(map: GoogleMap): Polygon {
        return map.addPolygon(PolygonOptions()
            .add(LatLng(0.0, 0.0))
            .strokeWidth(1.0f)
            .clickable(true)
        )
    }
}