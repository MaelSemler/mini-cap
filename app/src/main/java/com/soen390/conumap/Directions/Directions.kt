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


class Directions() {
    val map = Map

    lateinit var _directionText :String

    lateinit var _totalDistanceText:String
    lateinit var _totalTimeText : String
    lateinit var _infoPathText :String

    val directionArray : MutableList<String> = ArrayList<String>()
    val distanceArray: MutableList<String> = ArrayList<String>()
    val durationArray: MutableList<String>  = ArrayList<String>()


    init {
        _directionText ="Directions: "
        _totalDistanceText = "("
        _totalTimeText = ""
        _infoPathText="via "
    }

//    val directionText:LiveData<String>
//        get() = return _directionText
//    val totalDistanceText: LiveData<String>
//        get() = _totalDistanceText
//    val totalTimeText: LiveData<String>
//        get() = _totalTimeText
//    val infoPathText : LiveData<String>
//        get() = _infoPathText

    var textConverted = ""


    //From the JSON response, extract all needed informations such as direction(instruction), the distance and the duration

    suspend fun extractDirections(steps: JSONArray): String {
        var textConverted = "Direction:" + '\n'


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
    suspend fun cleanUpDirections(tempText:String):String{
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
        _directionText = (textSteps)
    }

    //Update TotalDistance
    fun updateTotalDistance(distance:String){
        _totalDistanceText += distance + ")"
    }

    //Update TotalDuration
    fun updateTotalDuration(duration:String){
        _totalTimeText = duration
    }

    //Update the information of the path
    fun updatePathInfo(routeDescription: String){
        _infoPathText += routeDescription
    }


}