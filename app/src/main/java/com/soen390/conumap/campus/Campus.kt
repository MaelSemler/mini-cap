package com.soen390.conumap.campus

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Campus (var name: String, var info: String, var location: LatLng, map: GoogleMap) {
    var marker: Marker

    init {
        this.marker = addCampusMarker(map)
    }

    // Adds marker for this building to the map passed as an argument.
    fun addCampusMarker(map: GoogleMap): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .alpha(0.0F)
                .title(name)
                .snippet(info)
        )
    }
}