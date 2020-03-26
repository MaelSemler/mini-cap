package com.soen390.conumap.path

import android.app.Activity
import android.provider.Settings
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.Directions.Directions
import com.soen390.conumap.map.Map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


//TODO: Implement Live Data Binding as in directions.kt
object path {
    //origin
//    val destination= Location()
    private var distance=0.0
    //private var transportationMethod="Car"
    //time
    //accessibilityFriendly

    val _directionText = MutableLiveData<String>()
    val _totalDistanceText = MutableLiveData<String>()
    val _totalTimeText = MutableLiveData<String>()
    val _infoPathText = MutableLiveData<String>()
    val _alternateText = MutableLiveData<String>()
    val _transportationMode = MutableLiveData<String>()
    val _alternateRouteId = MutableLiveData<Int>()
    val _alternateRouteIdMax = MutableLiveData<Int>()

    var directionsArray : ArrayList<Directions> = arrayListOf()

    val directionText:LiveData<String>
        get() = _directionText
    val totalDistanceText: LiveData<String>
        get() = _totalDistanceText
    val totalTimeText: LiveData<String>
        get() = _totalTimeText
    val infoPathText : LiveData<String>
        get() = _infoPathText
    val alternateText:LiveData<String>
        get() = _alternateText
    val transportationMode:LiveData<String>
        get() = _transportationMode
    val alternateRouteId:LiveData<Int>
        get() = _alternateRouteId
    val alternateRouteIdMax:LiveData<Int>
        get() = _alternateRouteIdMax

    val map = Map

    lateinit var dirObj :Directions

    init {
        _directionText.value = "Direction: "
        _totalDistanceText.value = ""
        _totalTimeText.value = ""
        _infoPathText.value =""
        _alternateText.value = ""
        _transportationMode.value = "driving"
        _alternateRouteId.value = 0
        _alternateRouteIdMax.value = 99
    }


 fun findDirections(activity: FragmentActivity){
        //TODO: Default origin is the current location
     //   val originLatLng = map.getCurrentLocation()
        val originLatLng = LatLng(45.525485, -73.5779957)//HardCoded Great Italian Restaurant
        //TODO:Destination is hardcoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now

        //TODO: Set Transportation mode
        //This can be walking, driving, bicycling, transit
        val transportationMode = getTransportationMode()
        //val transportationMode = "driving"//TODO: Hardcoded for now look at the line just before we should be able to retrieve it this way

        //TODO: Set Alternative On/Off
        //val alternativesOn = getAlternatives()
        val alternativesOn = true//TODO: Hardcoded that alternatives are turned off for now look at line right before: to be implemented

        //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)


     dirObj = Directions()

        GlobalScope.launch {
            route(
                activity,
                originLatLng,
                destinationLatLng,
                transportationMode,
                alternativesOn
            )//Calling the actual route function and passing all the needed parameters

        }

    }

fun resetDirections(){
    _directionText.value = "Direction: "
    _totalDistanceText.value = ""
    _totalTimeText.value = ""
    _infoPathText.value =""
    _alternateText.value = ""
    _transportationMode.value = ""
    _alternateRouteId.value = 0
    _alternateRouteIdMax.value = 99
}

    fun getTransportationMode(): String {
       return _transportationMode.value.toString()
    }

    fun switchOriginAndDestination()
    {
        //TODO: To be implemented
    }

    fun getAlternatives(): Int{
       return _alternateRouteId.value!!.toInt()
    }

    //Update the directionText
    fun updateSteps(textSteps: String){

        _directionText.value = textSteps
    }

    //Update TotalDistance
    fun updateTotalDistance(distance:String){
        _totalDistanceText.value += distance
    }

    //Update TotalDuration
    fun updateTotalDuration(duration:String){
        _totalTimeText.value = duration
    }

    //Update the information of the path
    fun updatePathInfo(routeDescription:String){
        _infoPathText.value += routeDescription
    }

    //Update the alternative routes
    fun updateAlternateText(totalTimeText: String, totalDistanceText: String, infoPathText: String){
        _alternateText.value += "  "+ totalTimeText + " " + totalDistanceText + "\n" + infoPathText +"\n\n"
    }

    fun setTransportationMode(mode:String){
        _transportationMode.value = mode
    }

    fun setTransportationDriving(){
        setTransportationMode("driving")
    }

    fun setTransportationCycling(){
        setTransportationMode("bicycling")
    }

    fun setTransportationWalking(){
        setTransportationMode("walking")
    }

    fun setTransportationTransit(){
        setTransportationMode("transit")
    }

    fun setAlternativeRouteMaxId(n:Int){
        _alternateRouteIdMax.value = n
    }
    fun setAlternativeRoute(n:Int){
        if (n <= _alternateRouteIdMax.value!!.toInt()){
            _alternateRouteId.value = n
        }
    }

}
