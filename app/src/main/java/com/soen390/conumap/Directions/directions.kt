package com.soen390.conumap.Directions

import android.app.Activity
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import org.json.JSONArray
import org.json.JSONObject


object directions {
    val map = Map

    val _directionText = MutableLiveData<String>()
    val _totalDistanceText = MutableLiveData<String>()
    val _totalTimeText = MutableLiveData<String>()
    val directionText:LiveData<String>
        get() = _directionText
    val totalDistanceText: LiveData<String>
        get() = _totalDistanceText
    val totalTimeText: LiveData<String>
        get() = _totalTimeText

    init {
        _directionText.value = "Directions: "
        _totalDistanceText.value = "("
        _totalTimeText.value = ""
    }

    var textConverted = ""


    fun routeTest(activity:Activity) {
        //TODO: Default origin is the current location
        val originLatLng = map.getCurrentLocation()
        //TODO:Destination is hardcoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now

        //TODO: Set Transportation mode
        //This can be walking, driving, bicycling, transit
        //val transportationMode = getTransportationMode()
        val transportationMode = "driving"//TODO: Hardcoded for now look at the line just before we should be able to retrieve it this way

        //TODO: Set Alternative On/Off
        //val alternativesOn = getAlternatives()
        val alternativesOn = false//TODO: Hardcoded that alternatives are turned off for now look at line right before: to be implemented

       //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)

        route(activity,originLatLng, destinationLatLng, transportationMode, alternativesOn)//Calling the actual route function and passing all the needed parameters
    }

    //Put the correct parameters inside the api call
    //This function returns the URL to make the GoogleMap API call
    private fun getGoogleMapRequestURL(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn:Boolean):String{
        if(alternativesOn)
            return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&alternative=true"+ "&key=" + activity.getString(R.string.apiKey)

        return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&key=" + activity.getString(R.string.apiKey)
    }


    //Route methods which makes the call get the response from Google Directions API and parse the JSON files to store everything inside arrays
    private fun route(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn: Boolean) {
        //Path is an arrayList that store every "steps"/path =>Will be used to draw the path
        val path: MutableList<List<LatLng>> = ArrayList()

        //Retrieve the correct URL to call the API
        val urlDirections = getGoogleMapRequestURL(activity, originLatLng, destinationLatLng, transportationMode, alternativesOn)

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

                //ExtractDirections and save it into the directionText blocks
                updateSteps(extractDirections(steps))
                updateTotalDistance(totalDistance)
                updateTotalDuration(totalDuration)

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
            com.android.volley.Response.ErrorListener { _ ->
            }) {}
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(directionsRequest)

        //Move the camera and zoom into the destination
        map.moveCamera(destinationLatLng, 18f)
    }


    //From the JSON response, extract all needed informations such as direction(instruction), the distance and the duration
    fun extractDirections(steps: JSONArray): String {
        var textConverted = "Direction:" + '\n'

        var directionArray = ArrayList<String>()
        var distanceArray = ArrayList<String>()
        var durationArray  = ArrayList<String>()



        for (i in 0 until steps.length()) {
            distanceArray.add(steps.getJSONObject(i).getJSONObject("distance").getString("text"))
            durationArray.add(steps.getJSONObject(i).getJSONObject("duration").getString("text"))

            var tempText = steps.getJSONObject(i).getString("html_instructions")

            directionArray.add(cleanUpDirections(tempText))
        }

        //Loop through everything retrieved and make it into a pretty displayable block text
        for(i in 0 until distanceArray.size){
            textConverted += (i + 1).toString() + ". " +  directionArray[i] + '\n' + '\t' + distanceArray[i] +'\n'
        }

        //return the appropriately formatted directions instruction block text
        return textConverted
    }

    //Function to get rid of all HTML tags from the direction instructions
    //return the instruction cleared of HTML tags
    fun cleanUpDirections(tempText:String):String{
        var tempText = tempText

        while(tempText.contains('<',true)){
            val leftBracketIndex = tempText.indexOf('<')
            val rightBracketIndex = tempText.indexOf('>')
            tempText= tempText.removeRange(leftBracketIndex,rightBracketIndex+1)
        }
        return tempText
    }

    //Update the directionText
    fun updateSteps(textSteps:String){
        _directionText.value = textSteps
    }

    //Update TotalDistance
    fun updateTotalDistance(distance:String){
        _totalDistanceText.value += distance + ")"
    }

    //Update TotalDuration
    fun updateTotalDuration(duration:String){
        _totalTimeText.value = duration
    }


}