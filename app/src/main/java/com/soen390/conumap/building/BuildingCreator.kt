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
        val sgwGM = Building(
            context.getString(R.string.sgwGMName),
            context.getString(R.string.sgwGMInfo),
            LatLng(45.495850, -73.578766),
            context.resources.getStringArray(R.array.buildingGMPoints),
            context.resources.getStringArray(R.array.sgwGMTarget),
            map,
            0.1f
        )
        buildings.add(sgwGM)
        val sgwMB = Building(
            context.getString(R.string.sgwMBName),
            context.getString(R.string.sgwMBInfo),
            LatLng(45.495418, -73.579169),
            context.resources.getStringArray(R.array.buildingMBPoints),
            context.resources.getStringArray(R.array.sgwMBTarget),
            map,
            0.2f
        )
        buildings.add(sgwMB)
        val sgwEV = Building(
            context.getString(R.string.sgwEVName),
            context.getString(R.string.sgwEVInfo),
            LatLng(45.495506, -73.577774),
            context.resources.getStringArray(R.array.buildingEVPoints),
            context.resources.getStringArray(R.array.sgwEVTarget),
            map,
            0.3f
        )
        buildings.add(sgwEV)
        val sgwFG = Building(
            context.getString(R.string.sgwFGName),
            context.getString(R.string.sgwFGInfo),
            LatLng(45.494373, -73.578332),
            context.resources.getStringArray(R.array.buildingFGPoints),
            context.resources.getStringArray(R.array.sgwFGTarget),
            map,
            0.4f
        )
        buildings.add(sgwFG)
        val sgwFB = Building(
            context.getString(R.string.sgwFBName),
            context.getString(R.string.sgwFBInfo),
            LatLng(45.494753, -73.577731),
            context.resources.getStringArray(R.array.buildingFBPoints),
            context.resources.getStringArray(R.array.sgwFBTarget),
            map,
            0.5f
        )
        buildings.add(sgwFB)
        val sgwLB = Building(
            context.getString(R.string.sgwLBName),
            context.getString(R.string.sgwLBInfo),
            LatLng(45.496990, -73.577951),
            context.resources.getStringArray(R.array.buildingLBPoints),
            context.resources.getStringArray(R.array.sgwLBTarget),
            map,
            0.6f
        )
        buildings.add(sgwLB)
        val sgwGN = Building(
            context.getString(R.string.sgwGNName),
            context.getString(R.string.sgwGNInfo),
            LatLng(45.493652, -73.576985),
            context.resources.getStringArray(R.array.buildingGNPoints),
            context.resources.getStringArray(R.array.sgwGNTarget),
            map,
            0.7f
        )
        buildings.add(sgwGN)
        val sgwLS = Building(
            context.getString(R.string.sgwLSName),
            context.getString(R.string.sgwLSInfo),
            LatLng(45.496232, -73.579491),
            context.resources.getStringArray(R.array.buildingLSPoints),
            context.resources.getStringArray(R.array.sgwLSTarget),
            map,
            0.8f
        )
        buildings.add(sgwLS)
        val sgwVA = Building(
            context.getString(R.string.sgwVAName),
            context.getString(R.string.sgwVAInfo),
            LatLng(45.495683, -73.573565),
            context.resources.getStringArray(R.array.buildingVAPoints),
            context.resources.getStringArray(R.array.sgwVATarget),
            map,
            0.9f
        )
        buildings.add(sgwVA)

        // LOY Buildings.
        val loyGE = Building(
            context.getString(R.string.loyGEName),
            context.getString(R.string.loyGEInfo),
            LatLng(45.456984, -73.640442),
            context.resources.getStringArray(R.array.buildingGEPoints),
            context.resources.getStringArray(R.array.loyGETarget),
            map,
            1.0f
        )
        buildings.add(loyGE)
        val loyCJ = Building(
            context.getString(R.string.loyCJName),
            context.getString(R.string.loyCJInfo),
            LatLng(45.457477, -73.640306),
            context.resources.getStringArray(R.array.buildingCJPoints),
            context.resources.getStringArray(R.array.loyCJTarget),
            map,
            1.1f
        )
        buildings.add(loyCJ)
        val loyAD = Building(
            context.getString(R.string.loyADName),
            context.getString(R.string.loyADInfo),
            LatLng(45.457973, -73.639890),
            context.resources.getStringArray(R.array.buildingADPoints),
            context.resources.getStringArray(R.array.loyADTarget),
            map,
            1.2f
        )
        buildings.add(loyAD)
        val loySP = Building(
            context.getString(R.string.loySPName),
            context.getString(R.string.loySPInfo),
            LatLng(45.457879, -73.641682),
            context.resources.getStringArray(R.array.buildingSPPoints),
            context.resources.getStringArray(R.array.loySPTarget),
            map,
            1.3f
        )
        buildings.add(loySP)
        val loyCC = Building(
            context.getString(R.string.loyCCName),
            context.getString(R.string.loyCCInfo),
            LatLng(45.458266, -73.640282),
            context.resources.getStringArray(R.array.buildingCCPoints),
            context.resources.getStringArray(R.array.loyCCTarget),
            map,
            1.4f
        )
        buildings.add(loyCC)
        val loyFC = Building(
            context.getString(R.string.loyFCName),
            context.getString(R.string.loyFCInfo),
            LatLng(45.458564, -73.639295),
            context.resources.getStringArray(R.array.buildingFCPoints),
            context.resources.getStringArray(R.array.loyFCTarget),
            map,
            1.5f
        )
        buildings.add(loyFC)
        val loyVL = Building(
            context.getString(R.string.loyVLName),
            context.getString(R.string.loyVLInfo),
            LatLng(45.458982, -73.638619),
            context.resources.getStringArray(R.array.buildingVLPoints),
            context.resources.getStringArray(R.array.loyVLTarget),
            map,
            1.6f
        )
        buildings.add(loyVL)
        val loySC = Building(
            context.getString(R.string.loySCName),
            context.getString(R.string.loySCInfo),
            LatLng(45.459085, -73.639221),
            context.resources.getStringArray(R.array.buildingVLPoints),
            context.resources.getStringArray(R.array.loySCTarget),
            map,
            1.7f
        )
        buildings.add(loySC)
        val loyPT = Building(
            context.getString(R.string.loyPTName),
            context.getString(R.string.loyPTInfo),
            LatLng(45.459325, -73.638907),
            context.resources.getStringArray(R.array.buildingVLPoints),
            context.resources.getStringArray(R.array.loyPTTarget),
            map,
            1.8f
        )
        buildings.add(loyPT)
        val loyPS = Building(
            context.getString(R.string.loyPSName),
            context.getString(R.string.loyPSInfo),
            LatLng(45.459720, -73.639819),
            context.resources.getStringArray(R.array.loyPSTarget), // TODO: Change to buildingPSPoints once it is created.
            context.resources.getStringArray(R.array.loyPSTarget),
            map,
            1.9f
        )
        buildings.add(loyPS)
        val loyPY = Building(
            context.getString(R.string.loyPYName),
            context.getString(R.string.loyPYInfo),
            LatLng(45.459028, -73.640591),
            context.resources.getStringArray(R.array.buildingPYPoints),
            context.resources.getStringArray(R.array.loyPYTarget),
            map,
            2.0f
        )
        buildings.add(loyPY)
        val loyRF = Building(
            context.getString(R.string.loyRFName),
            context.getString(R.string.loyRFInfo),
            LatLng(45.458588, -73.641055),
            context.resources.getStringArray(R.array.buildingRFPoints),
            context.resources.getStringArray(R.array.loyRFTarget),
            map,
            2.1f
        )
        buildings.add(loyRF)
        val loyHA = Building(
            context.getString(R.string.loyHAName),
            context.getString(R.string.loyHAInfo),
            LatLng(45.459431, -73.641248),
            context.resources.getStringArray(R.array.buildingHAPoints),
            context.resources.getStringArray(R.array.loyHATarget),
            map,
            2.2f
        )
        buildings.add(loyHA)
        val loyHB = Building(
            context.getString(R.string.loyHBName),
            context.getString(R.string.loyHBInfo),
            LatLng(45.459081, -73.641940),
            context.resources.getStringArray(R.array.buildingHAPoints),
            context.resources.getStringArray(R.array.loyHBTarget),
            map,
            2.3f
        )
        buildings.add(loyHB)
        val loyHC = Building(
            context.getString(R.string.loyHCName),
            context.getString(R.string.loyHCInfo),
            LatLng(45.459630, -73.642082),
            context.resources.getStringArray(R.array.buildingHAPoints),
            context.resources.getStringArray(R.array.loyHCTarget),
            map,
            2.4f
        )
        buildings.add(loyHC)

        return buildings
    }
}
