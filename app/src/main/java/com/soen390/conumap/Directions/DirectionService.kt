package com.soen390.conumap.Directions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import com.soen390.conumap.path.path
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.SynchronizedObject
import org.json.JSONArray
import org.json.JSONObject


object DirectionService {
    val map = Map
    val listOfPath = ArrayList<Directions>()
//    private val context: Context

    init {

    }

    //Put the correct parameters inside the api call
    //This function returns the URL to make the GoogleMap API call
    fun getGoogleMapRequestURL(activity: Activity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn:Boolean):String{
        if(alternativesOn)
            return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&alternatives=true"+ "&key=" + activity.getString(
                R.string.apiKey)

        return activity.getString(R.string.DirectionAPI)+ originLatLng.latitude + "," + originLatLng.longitude + "&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&mode=" + transportationMode + "&key=" + activity.getString(
            R.string.apiKey)
    }

    //This is route functions it ALREADY retrieves alternatives and store them as directions objects
    suspend fun route(activity: FragmentActivity, originLatLng: LatLng, destinationLatLng: LatLng, transportationMode: String, alternativesOn: Boolean) {
        coroutineScope{

            //Path is an arrayList that store every "steps"/path =>Will be used to draw the path
            val path: MutableList<List<LatLng>> = ArrayList()

            //Retrieve the correct URL to call the API

            val urlDirections = getGoogleMapRequestURL(activity, originLatLng, destinationLatLng, transportationMode, alternativesOn)

            //Making the Request
            launch(Dispatchers.IO) {
                val directionsRequest = object : StringRequest(
                    Request.Method.GET,
                    urlDirections,
                    com.android.volley.Response.Listener<String> { response ->
                        val jsonResponse = JSONObject(response)

                        val status = jsonResponse.getString("status")

                        if (status == "OK")
                        {
                            // This part to understand it look carefully at the JSON response sent by the API
                            val routes = jsonResponse.getJSONArray("routes")

                            val legs = routes.getJSONObject(0).getJSONArray("legs")
                            val steps = legs.getJSONObject(0).getJSONArray("steps")

                            for(i in 0  until routes.length()){
                                var dirObj : Directions = Directions()
                                val legs = routes.getJSONObject(i).getJSONArray("legs")
                                val steps = legs.getJSONObject(0).getJSONArray("steps")

                                //Retrieval total duration of the whole trip and total distance of the whole trip
                                val totalDistance =legs.getJSONObject(0).getJSONObject("distance").getString("text")
                                val totalDuration= legs.getJSONObject(0).getJSONObject("duration").getString("text")
                                val pathInfo = routes.getJSONObject(0).getString("summary")

                                //ExtractDirections and save it into the directionText blocks
                                val extractedCleanedDirections = dirObj.extractDirections(steps)

                                //Instantiate a directions Object (which represents a route)
                                dirObj.updateSteps(extractedCleanedDirections)
                                dirObj.updateTotalDistance(totalDistance)
                                dirObj.updateTotalDuration(totalDuration)
                                dirObj.updatePathInfo(pathInfo)

                                //List of Path is an ArrayList containing every alternative route
                                listOfPath.add(dirObj)
                                ///////////////////////////////////////////////////

                            }

                            //Update the display on the main thread
                            val n = com.soen390.conumap.path.path.getAlternatives()

                            activity.runOnUiThread {
                                com.soen390.conumap.path.path.updatePathInfo(listOfPath[n].getInfoPathText())
                                com.soen390.conumap.path.path.updateTotalDuration(listOfPath[n].getTotalTimeText())
                                com.soen390.conumap.path.path.updateTotalDistance(listOfPath[n].getTotalDistanceText())
                                com.soen390.conumap.path.path.updateSteps(listOfPath[n].getDirectionText())
                                var j =1
                                for (i in 0 until listOfPath.size){
                                    Log.d("DirectionServices", "Building Alternate list $i " + listOfPath[i].getInfoPathText())
                                    if (i != n){
                                        com.soen390.conumap.path.path.updateAlternateText("$j - "+listOfPath[i].getInfoPathText()+"\n")
                                        j++
                                    }
                                }
                            }

                            //This Draw on the Map the tracing of "Steps"
                            //TODO: This needs to be refactor into another function and process what "path" to display/draw on the map
                            for (i in 0 until steps.length()) {
                                val points =
                                    steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                                path.add(PolyUtil.decode(points))
                            }
                            for (i in 0 until path.size) {
                                map.getMapInstance().addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
                            }
                        } else {
                            // status NOT OK (No route from Google API)
                            //TODO display error message on phone
                            Toast.makeText(activity, "ERROR:  No answer from google API status $status", Toast.LENGTH_SHORT).show()
                        }
                    },
                    com.android.volley.Response.ErrorListener() {
                        @Override
                        fun onErrorResponse(error:VolleyError) {

                            Toast.makeText(activity,  (error.toString()), Toast.LENGTH_SHORT).show()
                        }
                    }) {}
                val requestQueue = Volley.newRequestQueue(activity)
                requestQueue.add(directionsRequest)
            }

            //Move the camera and zoom into the destination
            activity.runOnUiThread {
                map.moveCamera(destinationLatLng, 18f)
            }
        }
    }
}