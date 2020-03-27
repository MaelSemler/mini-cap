package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.Directions.Directions
import com.soen390.conumap.map.Map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Path {
    //origin
    // val destination= Location()
    //accessibilityFriendly
    private val pathviewModel= PathViewModel()
    private var distance=0.0
    private val alternativesOn= false
    private var transportationMode= pathviewModel.transportationMode.value
    val _PathDirectionText = MutableLiveData<String>()
    val _PathTotalDistanceText = MutableLiveData<String>()
    val _PathTotalTimeText = MutableLiveData<String>()
    var _infoPathText = MutableLiveData<String>()

    var directionsArray : ArrayList<Directions> = arrayListOf()

    val directionText = _PathDirectionText  //Left-hand variables are what are passed onto the DirectionsViewModel
    val totalDistanceText = _PathTotalDistanceText
    val totalTimeText = _PathTotalTimeText
    val infoPathText = _infoPathText


    val map = Map
    init {
        _PathDirectionText.value = "Directions: "
        _PathTotalDistanceText.value = " "
        _PathTotalTimeText.value = ""
        _infoPathText.value ="via"
    }

    fun setTransportMode(value:String){
        transportationMode = value
    }

 fun findDirections(activity: FragmentActivity){
        //TODO: Default origin is the current location
        val originLatLng = map.getCurrentLocation()
        //TODO:Destination is hardcoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now

        //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)
        GlobalScope.launch {
            if (transportationMode != null) {
                route(
                    activity,
                    originLatLng,
                    destinationLatLng,
                    transportationMode!!,
                    alternativesOn
                )
            }//Calling the actual route function and passing all the needed parameters
        }
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

        _PathDirectionText.value = textSteps
    }

    //Update TotalDistance
    fun updateTotalDistance(distance:String){
        _PathTotalDistanceText.value = distance
    }

    //Update TotalDuration
    fun updateTotalDuration(duration:String){
        _PathTotalTimeText.value = duration
    }

    //Update the information of the path
    fun updatePathInfo(routeDescription:String){
        _infoPathText.value = routeDescription
    }


}