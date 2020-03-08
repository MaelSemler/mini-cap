package com.soen390.conumap

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Building {
    var name: String
    var info: String
    var location: LatLng
    var marker: Unit

    constructor(name: String, info: String, location: LatLng, map: GoogleMap) {
        this.name = name
        this.info = info
        this.location = location
        this.marker = addBuildingMarker(map)
    }

    // Adds marker for this building to the map passed as an argument.
    fun addBuildingMarker(map: GoogleMap) {
        map.addMarker(MarkerOptions()
            .position(location)
            .alpha(0.0F)
            .title(name)
            .snippet(info)
        )
    }
}
