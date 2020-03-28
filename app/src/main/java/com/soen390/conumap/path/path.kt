package com.soen390.conumap.path

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.DirectionService.route
import com.soen390.conumap.map.Map
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//TODO: Implement Live Data Binding as in directions.kt
object path {
    //accessibilityFriendly
    //val alternativesOn = getAlternatives()
    private var distance=0.0
    var alternativesOn= true
    var transportationMode: String = "driving"
    private lateinit var originFromSearch:LatLng
    private lateinit var destinationFromSearch: LatLng
    val _PathDirectionText = MutableLiveData<String>()
    val _PathTotalDistanceText = MutableLiveData<String>()
    val _PathTotalTimeText = MutableLiveData<String>()
    var _infoPathText = MutableLiveData<String>()

    val map = Map

    val _directionText = MutableLiveData<String>()
    val _totalDistanceText = MutableLiveData<String>()
    val _totalTimeText = MutableLiveData<String>()
    val _infoPathText = MutableLiveData<String>()
    val _alternateText = MutableLiveData<String>()
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
        _alternateRouteId.value = 0
        _alternateRouteIdMax.value = 99

    fun setOrigin(value:LatLng){
        originFromSearch = value
    }

    fun setDestination(value:LatLng){
        destinationFromSearch = value
    }

 fun findDirections(activity: FragmentActivity){

     val originLatLng = originFromSearch
     val destinationLatLng = destinationFromSearch

        //TODO: Origin and Destination should have a title
        map.addMarker(originLatLng,("This is the origin"))
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

fun resetDirections(){
    _directionText.value = "Direction: "
    _totalDistanceText.value = ""
    _totalTimeText.value = ""
    _infoPathText.value =""
    _alternateText.value = ""
    _alternateRouteId.value = 0
    _alternateRouteIdMax.value = 99
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

    //Update the alternative routes
    fun updateAlternateText(totalTimeText: String, totalDistanceText: String, infoPathText: String){
        _alternateText.value += "  "+ totalTimeText + " " + totalDistanceText + "\n" + infoPathText +"\n\n"
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
