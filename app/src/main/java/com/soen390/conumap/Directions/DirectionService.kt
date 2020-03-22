package com.soen390.conumap.Directions

import android.app.Activity
import android.graphics.Color
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import org.json.JSONObject


object DirectionService {
    val map = Map

    init {

    }


    //Put the correct parameters inside the api call
    //This function returns the URL to make the GoogleMap API call
    fun getGoogleMapRequestURL(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn:Boolean):String{
        if(alternativesOn)
            return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&alternative=true"+ "&key=" + activity.getString(
                R.string.apiKey)

        return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&key=" + activity.getString(
            R.string.apiKey)
    }

    //Route methods which makes the call get the response from Google Directions API and parse the JSON files to store everything inside arrays
   fun route(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn: Boolean) :Directions {
        //Path is an arrayList that store every "steps"/path =>Will be used to draw the path
        val path: MutableList<List<LatLng>> = ArrayList()

        //Retrieve the correct URL to call the API
        val urlDirections = getGoogleMapRequestURL(activity, originLatLng, destinationLatLng, transportationMode, alternativesOn)

        var dirObj : Directions = Directions()

        //Making the Request
        val directionsRequest = object : StringRequest(
            Request.Method.GET,
            urlDirections,
            com.android.volley.Response.Listener<String> { response ->
                val jsonResponse = JSONObject(response)
                // This part to understand it look carefully at the JSON response sent by the API
                val routes = jsonResponse.getJSONArray("routes")
                val legs = routes.getJSONObject(0).getJSONArray("legs")
                val steps = legs.getJSONObject(0).getJSONArray("steps")

                //Retrieval total duration of the whole trip and total distance of the whole trip
                val totalDistance =legs.getJSONObject(0).getJSONObject("distance").getString("text")
                val totalDuration= legs.getJSONObject(0).getJSONObject("duration").getString("text")
                val pathInfo = routes.getJSONObject(0).getString("summary")

                //ExtractDirections and save it into the directionText blocks
                dirObj.updateSteps(dirObj.extractDirections(steps))
                dirObj.updateTotalDistance(totalDistance)
                dirObj.updateTotalDuration(totalDuration)
                dirObj.updatePathInfo(pathInfo)

                //Draw the path in path in red color
                for (i in 0 until steps.length()) {
                    val points =
                        steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                    path.add(PolyUtil.decode(points))
                }
                for (i in 0 until path.size) {
                    map.getMapInstance().addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
                }
            },
            com.android.volley.Response.ErrorListener { error ->
                val responseBody = String(error.networkResponse.data)
                val data = JSONObject(responseBody)
                val message = data.optString("msg")
                print(message)
                dirObj.updateSteps(error.message.toString())
            }) {}
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(directionsRequest)

        //Move the camera and zoom into the destination
        map.moveCamera(destinationLatLng, 18f)
        return dirObj
    }






}