package com.soen390.conumap.Building

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class Building {
        var name: String
        var info: String
        var location: LatLng
        var marker: Marker
        var touchTarget: Polygon

        constructor(name: String, info: String, location: LatLng, map: GoogleMap) {
            this.name = name
            this.info = info
            this.location = location
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