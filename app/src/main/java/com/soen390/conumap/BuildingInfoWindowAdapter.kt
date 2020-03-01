package com.soen390.conumap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class BuildingInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
    private val view: View
    private val context: Context

    constructor(ctx: Context) {
        context = ctx
        view = LayoutInflater.from(context).inflate(R.layout.building_info_window, null)
    }

    private fun renderText(mkr: Marker, vw: View) {
        var title = mkr.title
        var buildingName = vw.findViewById<TextView>(R.id.building_name)
        buildingName.text = title

        var snippet = mkr.snippet
        var buildingDetails = vw.findViewById<TextView>(R.id.building_details)
        buildingDetails.text = snippet
    }

    override fun getInfoContents(mkr: Marker?): View? {
        if (mkr != null) {
            renderText(mkr, view)
        }
        return view
    }

    override fun getInfoWindow(mkr: Marker?): View? {
        if (mkr != null) {
            renderText(mkr, view)
        }
        return view
    }
}