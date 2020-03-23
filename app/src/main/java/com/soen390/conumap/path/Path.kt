package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.Directions.Directions
import com.soen390.conumap.map.Map

object Path {
    //origin
    // val destination= Location()
    //accessibilityFriendly
    var pathViewModel= PathViewModel()
    private var distance=0.0
    private var alternativesOn: Boolean = false //initial value is false
    private lateinit var transportationMode: String
    val _directionText = MutableLiveData<String>()
    val _totalDistanceText = MutableLiveData<String>()
    val _totalTimeText = MutableLiveData<String>()
    val _infoPathText = MutableLiveData<String>()

    var directionsArray : ArrayList<Directions> = arrayListOf()

    val directionText:LiveData<String>
        get() = _directionText
    val totalDistanceText: LiveData<String>
        get() = _totalDistanceText
    val totalTimeText: LiveData<String>
        get() = _totalTimeText
    val infoPathText : LiveData<String>
        get() = _infoPathText


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

        //Retrieves transportation mode
        //This can be walking, driving, bicycling, transit
        transportationMode = pathViewModel.getTransportationMode()

        //TODO: Set Alternative On/Off
        //val alternativesOn = pathViewModel.getAlternative()

        //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)

        var dirObj = Directions()
        route(activity,originLatLng, destinationLatLng, transportationMode, alternativesOn)//Calling the actual route function and passing all the needed parameters
        _directionText.postValue(dirObj.textConverted)
        updatePathInfo(dirObj.infoPathText.value.toString())
        updateSteps((dirObj.directionText).value.toString())
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