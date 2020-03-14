package com.soen390.conumap.building

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.soen390.conumap.R

// Responsible for creating all buildings on both campuses.
object BuildingCreator {
    private lateinit var context: Context
    private var buildings: ArrayList<Building> = arrayListOf()

    // Context is needed for some resources.
    fun setContext(ctx: Context) { context = ctx }

    // Creates the Building objects and adds them to the buildings ArrayList.
    fun createBuildings(map: GoogleMap):ArrayList<Building> {
        //TODO ADD THE BUILDINS HERE

        // SGW Buildings.
        val sgwH = Building(
            context.getString(R.string.sgwHName),
            context.getString(R.string.sgwHInfo),
            LatLng(45.497304, -73.578923),
            map,
            0.0f,
            context.resources.getStringArray(R.array.buildingHPoints)
        )
        buildings.add(sgwH)
        val sgwGM = Building(
            context.getString(R.string.sgwGMName),
            context.getString(R.string.sgwGMInfo),
            LatLng(45.495850, -73.578766),
            map,
            1.0f,
            context.resources.getStringArray(R.array.buildingGMPoints)
        )
        buildings.add(sgwGM)
        //TODO ADD THE BUILDINGS IN THE ARRAYLIST

        return buildings
    }

    // Adds the visible building outlines to represent each Concordia building.
//    fun createOutlines(map: GoogleMap) {
//
//        //TODO ADD POLYGONS HERE WITH THE ZINDEX
//        val sgwHOutline  = PolygonOptions()
//            .add(
//                LatLng(45.496832, -73.578850),
//                LatLng(45.497173, -73.579553),
//                LatLng(45.497729, -73.579034),
//                LatLng(45.497380, -73.5783300),
//                LatLng(45.496832, -73.578850)
//            )
//            .fillColor(context.getColor(R.color.colorAccent))
//            .strokeWidth(0.1f)
//        map.addPolygon(sgwHOutline)
//        val buildingGM = PolygonOptions()
//            .add(
//                LatLng(45.495651, -73.578809),
//                LatLng(45.495780, -73.579088),
//                LatLng(45.495761, -73.579105),
//                LatLng(45.495783, -73.579151),
//                LatLng(45.496133, -73.578808),
//                LatLng(45.495977, -73.578482)
//            ).fillColor(R.color.colorAccent).strokeWidth(0.1f).clickable(true).zIndex(2.0F)
//        map.addPolygon(buildingGM)
//    }

    // Adds clickable touch targets, which are transparent polygons over each building.
    // When these are clicked, the popup with the building information is displayed.
    fun createTouchTargets(map: GoogleMap) {
        val sgwHTarget = PolygonOptions()
            .add(
                LatLng(45.496832, -73.578850),
                LatLng(45.497173, -73.579553),
                LatLng(45.497729, -73.579034),
                LatLng(45.497380, -73.5783300),
                LatLng(45.496832, -73.578850)
            )
            .fillColor(Color.argb(0, 0, 0, 0))
            .strokeColor(Color.argb(0, 0, 0, 0))
            .clickable(true)
            .zIndex(0.0f)
        map.addPolygon(sgwHTarget)
    }
}
