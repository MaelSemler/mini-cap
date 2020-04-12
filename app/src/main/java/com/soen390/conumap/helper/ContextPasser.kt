package com.soen390.conumap.helper

import android.content.Context
//import com.soen390.conumap.SVGConverter.FloorPlanTransformation
import com.soen390.conumap.SVGConverter.SvgConverter
import com.soen390.conumap.building.BuildingCreator
import com.soen390.conumap.map.Map
import com.soen390.conumap.permission.Permission

object ContextPasser {
    private lateinit var context: Context

    // Context passed to ContextPasser and all other files that require context.
    fun setContexts(ctx: Context) {
        context = ctx

        Map.setContext(context)
        Permission.setContext(context)
        BuildingCreator.setContext(context)
        DeviceLocationChecker.setUp(context)

//        SvgConverter.setContext(context)

    }

    fun setContextIndoor(ctx:Context){
        context = ctx
        SvgConverter.setContext(context)
//
//        FloorPlanTransformation.setContext(context)
//
    }

}
