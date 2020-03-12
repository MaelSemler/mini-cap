package com.soen390.conumap

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions

class Building {
    var name: String
    var info: String
    var location: LatLng
    var marker: Marker
    var touchTarget: Polygon
    var coordinateArray: Array<String>

    constructor(name: String, info: String, location: LatLng, map: GoogleMap) {
        this.name = name
        this.info = info
        this.location = location
        this.marker = addBuildingMarker(map)
        this.touchTarget = addTouchTarget(map)
        this.coordinateArray = arrayOf<String>()
    }

    constructor(name: String, info: String, location: LatLng, map: GoogleMap, coordinateArray: Array<String>) {
        this.name = name
        this.info = info
        this.location = location
        this.marker = addBuildingMarker(map)
        this.touchTarget = addTouchTarget(map)
        this.coordinateArray = coordinateArray
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

    fun generatePolygonShape(): PolygonOptions{
        val concordiaRed = Color.rgb(147,35,57)
        var polygon = PolygonOptions()
        for (i in coordinateArray.indices) {
            if (i % 2 == 0) {
                var latitude = coordinateArray[i].toDouble()
                var longitude = coordinateArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                polygon.add(latlng)
            }
        }
        polygon.fillColor(concordiaRed).strokeWidth(0.1f)
        return polygon
    }
}
