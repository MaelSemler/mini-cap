package com.soen390.conumap.path

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.Directions.Directions
import com.soen390.conumap.map.Map


//TODO: Implement Live Data Binding as in directions.kt
object path {
    //origin
    // val destination= Location()
    private var distance=0.0
    //time
    //accessibilityFriendly
    val _directionText = MutableLiveData<String>()
    val _totalDistanceText = MutableLiveData<String>()
    val _totalTimeText = MutableLiveData<String>()
    val _infoPathText = MutableLiveData<String>()
    val _transportationMode= MutableLiveData<String>()

    var directionsArray : ArrayList<Directions> = arrayListOf()

    val directionText:LiveData<String>
        get() = _directionText
    val totalDistanceText: LiveData<String>
        get() = _totalDistanceText
    val totalTimeText: LiveData<String>
        get() = _totalTimeText
    val infoPathText : LiveData<String>
        get() = _infoPathText
    val transportationMode : LiveData<String>
        get()= _transportationMode

    val map = Map

    init {
        _directionText.value = "Directions: "
        _totalDistanceText.value = "("
        _totalTimeText.value = ""
        _infoPathText.value ="via "
    }


    @Synchronized fun findShortestDirections(activity: FragmentActivity){
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

        var dirObj = Directions()
        route(activity,originLatLng, destinationLatLng, transportationMode, alternativesOn)//Calling the actual route function and passing all the needed parameters
        _directionText.postValue(dirObj.textConverted)
        updatePathInfo(dirObj.infoPathText.value.toString())
//        updateSteps((dirObj.directionText).value.toString())
        updateTotalDistance(dirObj.totalDistanceText.value.toString())
        updateTotalDuration(dirObj.totalTimeText.value.toString())
    }

    fun switchOriginAndDestination()
    {
        //TODO: To be implemented
    }

    fun getAlternatives()
    {
        //TODO: To be implemented
    }

    //Update the directionText
    fun updateSteps(textSteps: String){

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

    //Update the information of the path
    fun updatePathInfo(routeDescription:String){
        _infoPathText.value += routeDescription
    }


}