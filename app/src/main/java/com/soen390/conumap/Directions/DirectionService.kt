package com.soen390.conumap.Directions

import android.app.Activity
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
import com.google.android.gms.maps.model.Polyline
import com.soen390.conumap.path.Path
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.soen390.conumap.R
import com.soen390.conumap.map.Map
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


object DirectionService {
    val map = Map
    var listOfPath = ArrayList<Directions>()
    var polyline= ArrayList<Polyline>()
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

            listOfPath.clear()// Need to make sure it is clear, because if user is changing origin or destination and make a new search

            //Retrieve the correct URL to call the API
            val urlDirections = getGoogleMapRequestURL(activity, originLatLng, destinationLatLng, transportationMode, alternativesOn)
            Log.d("DirectionServices", "GoogleMAp URL $urlDirections ")

            //Making the Request
            launch(Dispatchers.IO) {
                val directionsRequest = object : StringRequest(
                    Request.Method.GET,
                    urlDirections,
                    com.android.volley.Response.Listener<String> { response ->
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")

                        if (status == "OK") {
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

                                //Instantiate a directions Object (which represent a route)
                                dirObj.updateSteps(extractedCleanedDirections)
                                dirObj.updateTotalDistance(totalDistance)
                                dirObj.updateTotalDuration(totalDuration)
                                dirObj.updatePathInfo(pathInfo)
                                dirObj.updateMapSteps(steps)

                                //List of Path is an ArrayList containing every alternative route
                                listOfPath.add(dirObj)
                                ///////////////////////////////////////////////////
                            }
                            //Update the display on the main thread
                            //TODO: THIS IS WHERE YOU EITHER CALL A FUNCTION THAT WILL LOGICALLY CHOOSE WHICH PATH TO DISPLAY ON THE SCREEN
                            var n = com.soen390.conumap.path.Path.getAlternatives()
                            if ( n >= listOfPath.size){
                                n = 0
                                com.soen390.conumap.path.Path.setAlternativeRoute(0)
                                Log.e("DirectionService", "Alternate Route Id > List of recorded routes")
                            }

                            //TODO: Currently hardcoded to return and display the first route only, but the rest are stored inside of listOfPath
                            activity.runOnUiThread {
                                displayOnScreenPath(listOfPath,n)

                            }

                            ResetPathDrawing()
                            //This Draw on the Map the tracing of "Steps"
                            drawPath(steps, Color.RED, false)     // Draw main path
                            // Draws alternate routes
                            for (i in 0 until listOfPath.size) {
                                if (i != n) {
                                    drawPath(listOfPath[i].getMapSteps(), Color.GRAY, true)
                                }
                            }
                        } else {
                            // status NOT OK (No route from Google API)
                            //TODO display error message on phone
                            Toast.makeText(activity, "ERROR:  No answer from google API - status: $status", Toast.LENGTH_SHORT).show()
                        }
                    },
                    com.android.volley.Response.ErrorListener() {
                        @Override
                        fun onErrorResponse(error:VolleyError) {
                            Toast.makeText(activity,  (error.toString()), Toast.LENGTH_SHORT).show();
                        }
                    })
                {}
                val requestQueue = Volley.newRequestQueue(activity)
                requestQueue.add(directionsRequest)
            }
            //Move the camera and zoom into the destination
            activity.runOnUiThread {
                map.moveCamera(destinationLatLng, 18f)
            }

        }
    }

    private fun drawPath(steps: JSONArray, color: Int, dotted: Boolean) {
        //Path is an arrayList that store every "steps"/path =>Will be used to draw the path
        val path: MutableList<List<LatLng>> = ArrayList()

        val PATTERN_DASH_LENGTH_PX: Float = 20f
        val PATTERN_GAP_LENGTH_PX: Float = 20f
        val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX)
        val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX)
        // Create a stroke pattern of a gap followed by a dash.
        val PATTERN_POLYGON_ALPHA: List<PatternItem> = Arrays.asList(GAP, DASH)

        val polyOptions = PolylineOptions()
        if (dotted){
            polyOptions.pattern(PATTERN_POLYGON_ALPHA)
        }

        var points=""
        for (i in 0 until steps.length()) {
            points =
                steps.getJSONObject(i).getJSONObject("polyline").getString("points")
            path.add(PolyUtil.decode(points))
        }
        for (i in 0 until path.size) {
            polyline.add(map.getMapInstance().addPolyline(polyOptions.addAll(path[i]).color(color))) //add polyline to arraylist
            polyline[i]   //draw polyline
        }
    }

    fun ResetPathDrawing(){
        if (polyline.isNotEmpty())
        {
            for (i in 0 until polyline.size)
            {polyline[i].remove()}   //removes each polyline in array list to redraw new polyline
        }
    }

    fun displayOnScreenPath(listOfPath:ArrayList<Directions>,n:Int){
        Path.updatePathInfo(listOfPath[n].getInfoPathText())
        Path.updateTotalDuration(listOfPath[n].getTotalTimeText())
        Path.updateTotalDistance(listOfPath[n].getTotalDistanceText())
        Path.updateSteps(listOfPath[n].getDirectionText())
        Path.setAlternativeRouteMaxId(listOfPath.size)
        Path.resetAlternateText()
        for (i in 0 until listOfPath.size){
            Log.d("DirectionServices", "Building Alternate list $i " + listOfPath[i].getInfoPathText())
            if (i != n){
                com.soen390.conumap.path.Path.updateAlternateText(listOfPath[i].getTotalTimeText(), listOfPath[i].getTotalDistanceText() ,listOfPath[i].getInfoPathText())
            }
        }
    }

}