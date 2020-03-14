package com.soen390.conumap.building

import android.content.Context
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
            context.resources.getStringArray(R.array.buildingHPoints),
            context.resources.getStringArray(R.array.sgwHTarget),
            map,
            0.0f
        )
        buildings.add(sgwH)

        // LOY Buildings.
//        val loyGE = Building(
//            context.getString(R.string.loyGEName),
//            context.getString(R.string.loyGEInfo),
//            LatLng(45.456984, -73.640442),
//            context.resources.getStringArray(R.array.buildingGEPoints),
//            context.resources.getStringArray(R.array.sgwHTarget)
//        )

        return buildings
    }
}
