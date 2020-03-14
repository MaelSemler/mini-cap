package com.soen390.conumap.Directions

import android.app.Activity
import android.graphics.Color
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.soen390.conumap.MainActivity
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import org.json.JSONArray
import org.json.JSONObject


object directions {
    val map = Map

    val _directionText = MutableLiveData<String>()
    val directionText:LiveData<String>
        get() = _directionText

    init {
        _directionText.value = "Directions: Null"
    }
    var textConverted = ""

    //TODO: This will be removed later. It is simply a Temporary helper method for now
    fun routeTest(activity:Activity) {
        //TODO: Default origin is the current location
        val originLatLng = map.getCurrentLocation()
        //TODO:Destination is hardcoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now
       //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)

        route(activity,originLatLng, destinationLatLng)
    }
    
    //Route method to be called once we are given the origin and destination 
    private fun route(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng) {
        val path: MutableList<List<LatLng>> = ArrayList()
        val urlDirections = activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&key=" + activity.getString(R.string.apiKey)


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
                updateSteps(extractDirections(steps))

                for (i in 0 until steps.length()) {
                    val points =
                        steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                    path.add(PolyUtil.decode(points))
                }
                for (i in 0 until path.size) {
                    map.getMapInstance().addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
                }
            },
            com.android.volley.Response.ErrorListener { _ ->
            }) {}
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(directionsRequest)

        map.moveCamera(destinationLatLng, 18f)
    }


    //Method to trim all the tags from the JSON directions retrieved from Google API
    fun extractDirections(steps: JSONArray): String {
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
        //Return the cleaned instructions
        return textConverted
    }

    //Update the Direction text box
    fun updateSteps(textSteps:String){
        _directionText.value = textSteps
    }


}
