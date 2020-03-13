package com.soen390.conumap.Directions

import android.graphics.Color
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.soen390.conumap.MainActivity
import android.view.View
import android.widget.Button
import com.soen390.conumap.R
import org.json.JSONObject
import org.json.JSONArray
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.maps.android.PolyUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*


class directions(map:GoogleMap) {
    val map = map


    fun routeTest(activity: MainActivity) {
        val originLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now
//        //TODO: Origin and Destination should have a title
//        this.map!!.addMarker(MarkerOptions().position(originLatLng).title("This is the origin"))
//        this.map!!.addMarker(MarkerOptions().position(destinationLatLng).title("This is the destination"))
//        this.map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 14.5f))

        //FOR SOME REASON THIS MAKES EVERYTHING CRASH
//        val totalDist :TextView = findViewById(R.id.totalDistance)
//        val totalTime: TextView = findViewById(R.id.totalTime)

        route(originLatLng, destinationLatLng)
    }

    private fun route(originLatLng: LatLng, destinationLatLng: LatLng) {
        val path: MutableList<List<LatLng>> = ArrayList()
        val urlDirections =
            getString(R.string.DirectionAPI) + "origin=" + originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&key=" + getString(
                R.string.apiKey
            )

        val directionsRequest = object : StringRequest(
            Request.Method.GET,
            urlDirections,
            com.android.volley.Response.Listener<String> { response ->
                val jsonResponse = JSONObject(response)
                // Get routes
                val routes = jsonResponse.getJSONArray("routes")
                val legs = routes.getJSONObject(0).getJSONArray("legs")
                val steps = legs.getJSONObject(0).getJSONArray("steps")

                val totalDistance =legs.getJSONObject(0).getJSONObject("distance").getString("text")
                val totalDuration= legs.getJSONObject(0).getJSONObject("duration").getString("text")


                //Clean up the directions
                extractDirections(steps)

                for (i in 0 until steps.length()) {
                    val points =
                        steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                    path.add(PolyUtil.decode(points))
                }
                for (i in 0 until path.size) {
                    this.map!!.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
                }
            },
            com.android.volley.Response.ErrorListener { _ ->
            }) {}
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(directionsRequest)

        map.setOnInfoWindowClickListener(this)

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 18f))
    }


    fun extractDirections(steps: JSONArray) {
//        val directionText: TextView = findViewById(R.id.Directions)
        var textConverted = "Direction:" + '\n'

        var directionArray = ArrayList<String>()
        var distanceArray = ArrayList<String>()
        var durationArray  = ArrayList<String>()



        for (i in 0 until steps.length()) {
            distanceArray.add(steps.getJSONObject(i).getJSONObject("distance").getString("text"))
            durationArray.add(steps.getJSONObject(i).getJSONObject("duration").getString("text"))

            var tempText = steps.getJSONObject(i).getString("html_instructions")
            while(tempText.contains('<',true)){
                val leftBracketIndex = tempText.indexOf('<')
                val rightBracketIndex = tempText.indexOf('>')
                tempText= tempText.removeRange(leftBracketIndex,rightBracketIndex+1)
            }
            directionArray.add(tempText)
        }

        for(i in 0 until distanceArray.size){
            textConverted += (i + 1).toString() + ". " +  directionArray[i] + '\n' + '\t' + distanceArray[i] +'\n'
        }
        //Put everything inside of the directionTextBox
//        directionText.text = textConverted

    }
}