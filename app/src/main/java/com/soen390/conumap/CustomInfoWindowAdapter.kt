package com.soen390.conumap

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
    private val window : View
    private val context : Context

    constructor(ctx: Context) {
        context = ctx
        window = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null)
    }

    fun renderText(mkr: Marker, vw: View) {
        var title = mkr.title
        var buildingName = vw.findViewById<TextView>(R.id.building_name)
        buildingName.text = title

        var snippet = mkr.snippet
        var campus = vw.findViewById<TextView>(R.id.campus)
        campus.text = snippet
    }

    override fun getInfoContents(p0: Marker?): View? {
        if (p0 != null) {
            renderText(p0, window)
        }
        return window
    }

    override fun getInfoWindow(p0: Marker?): View {
        if (p0 != null) {
            renderText(p0, window)
        }
        return window
    }
}