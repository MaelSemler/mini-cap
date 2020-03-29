package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.map.Map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

object Path {
    //accessibilityFriendly
    //val alternativesOn = getAlternatives()
    private var distance = 0.0
    var alternativesOn = true
    var transportationMode: String = "driving"
    private lateinit var originFromSearch: LatLng
    private lateinit var destinationFromSearch: LatLng
    val _PathDirectionText = MutableLiveData<String>()
    val _PathTotalDistanceText = MutableLiveData<String>()
    val _PathTotalTimeText = MutableLiveData<String>()
    var _infoPathText = MutableLiveData<String>()
    val _PathAlternateText = MutableLiveData<String>()
    val _alternateRouteId = MutableLiveData<Int>(0)
    val _alternateRouteIdMax = MutableLiveData<Int>(99)

    val map = Map

    fun setOrigin(value: LatLng) {
        originFromSearch = value
    }

    fun setDestination(value: LatLng) {
        destinationFromSearch = value
    }

    fun findDirections(activity: FragmentActivity) {

        val originLatLng = originFromSearch
        val destinationLatLng = destinationFromSearch

        //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng, ("This is the origin"))
        map.addMarker(destinationLatLng, "Destination")
        map.moveCamera(originLatLng, 14.5f)
        GlobalScope.launch {
            route(
                activity,
                originLatLng,
                destinationLatLng,
                transportationMode,
                alternativesOn
            )
            //Calling the actual route function and passing all the needed parameters
        }
    }

    fun switchOriginAndDestination() {
        //TODO: To be implemented
    }

    fun getAlternatives(): Int {
        return _alternateRouteId.value!!.toInt()
    }

    //Update the directionText
    fun updateSteps(textSteps: String) {

        _PathDirectionText.value = textSteps
    }

    //Update TotalDistance
    fun updateTotalDistance(distance: String) {
        _PathTotalDistanceText.value = distance
    }

    //Update TotalDuration
    fun updateTotalDuration(duration: String) {
        _PathTotalTimeText.value = duration
    }

    //Update the information of the path
    fun updatePathInfo(routeDescription: String) {
        _infoPathText.value = routeDescription
    }

    //Update the alternative routes
    fun updateAlternateText(
        totalTimeText: String,
        totalDistanceText: String,
        infoPathText: String
    ) {
        _PathAlternateText.value += "  " + totalTimeText + " " + totalDistanceText + "\n" + infoPathText + "\n\n"
    }

    fun setAlternativeRouteMaxId(n: Int) {
        _alternateRouteIdMax.value = n
    }

    fun setAlternativeRoute(n: Int) {
        if (n <= _alternateRouteIdMax.value!!.toInt()) {
            _alternateRouteId.value = n
        }
    }

    fun resetDirections() {
        _PathDirectionText.value = "Direction: "
        _PathTotalDistanceText.value = ""
        _PathTotalTimeText.value = ""
        _infoPathText.value = ""
        _PathAlternateText.value = ""
        _alternateRouteId.value = 0
        _alternateRouteIdMax.value = 99
    }

    fun setTransportation(mode: String){
        Path.transportationMode=mode
    }

    fun getTransportation():String{
        return Path.transportationMode
    }
}