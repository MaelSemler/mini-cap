package com.soen390.conumap.building

import android.content.res.Resources
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.soen390.conumap.R

// Class responsible for creating all buildings on both campuses.
object BuildingCreator {
    private var buildings: ArrayList<Building> = arrayListOf()

    fun createBuildings(map: GoogleMap):ArrayList<Building> {
        //TODO ADD THE BUILDINS HERE
        // SGW Buildings.
        var sgwH = Building(
            "Resources.getSystem().getString(R.string.sgwHName)",
            "Resources.getSystem().getString(R.string.sgwHInfo)",
            LatLng(45.497304, -73.578923),
            map,
            1.0f
        )
        /*val sgwGM = Building(
            Resources.getSystem().getString(R.string.sgwGMName),
            Resources.getSystem().getString(R.string.sgwGMInfo),
            LatLng(45.495850, -73.578766),
            map,
            2.0f
        )*/

        //TODO ADD THE BUILDINGS IN THE ARRAYLIST
        buildings.add(sgwH)
        return buildings
    }

    fun createPolygons(map: GoogleMap){

        //TODO ADD POLYGONS HERE WITH THE ZINDEX
        val buildingH  = PolygonOptions()
            .add(
                LatLng(45.496832, -73.578850),
                LatLng(45.497173, -73.579553),
                LatLng(45.497729, -73.579034),
                LatLng(45.497380, -73.5783300),
                LatLng(45.496832, -73.578850)
            ).fillColor(R.color.buildingOutline).strokeWidth(0.1f).clickable(true).zIndex(1.0F)
        val buildingGM = PolygonOptions()
            .add(
                LatLng(45.495651, -73.578809),
                LatLng(45.495780, -73.579088),
                LatLng(45.495761, -73.579105),
                LatLng(45.495783, -73.579151),
                LatLng(45.496133, -73.578808),
                LatLng(45.495977, -73.578482)
            ).fillColor(R.color.buildingOutline).strokeWidth(0.1f).clickable(true).zIndex(2.0F)
        map.addPolygon(buildingGM)
        map.addPolygon(buildingH)
    }
}
