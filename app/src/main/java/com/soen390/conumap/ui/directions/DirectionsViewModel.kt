package com.soen390.conumap.ui.directions

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.path.Path

class DirectionsViewModel : ViewModel() {

    // Return this when directions are determined
    val returnDirectionText = Path._PathDirectionText //This information is what is used to display on the Directions Fragment UI
    val returnTotalDistanceText = Path._PathTotalDistanceText
    val returnTotalTimeText = Path._PathTotalTimeText
    val returnInfoPathText= Path._infoPathText

    private var destinationName: String? = null
    private var destinationAddress: String? = null
    private var destinationLocation: LatLng? = null
    private var originName: String? = null
    private var originAddress: String? = null
    private var originLocation: LatLng? = null
    private var transportationMode: String= "driving"

    fun getDestinationName(): String? {
        return destinationName
    }

    fun getOriginName(): String?{
        return originName
    }

    fun getDestinationLocation():LatLng?{
        return destinationLocation
    }

    fun getOriginLocation(): LatLng? {
        return originLocation
    }
    fun getDestinationAddress(): String? {
        return destinationAddress
    }

    fun getOriginAddress(): String?{
        return originAddress
    }

    fun setOriginName(name:String){
        originName= name
    }

    fun setDestinationName(name:String){
        destinationName=name
    }

    fun setOriginAddress(name:String){
        originAddress= name
    }

    fun setDestinationAddress(name:String){
        destinationAddress=name
    }

    fun setOriginLocation(place:LatLng){
        originLocation= place
    }

    fun setDestinationLocation(place:LatLng){
        destinationLocation=place
    }



    fun setDestinationCompletely(name: String, location: LatLng, address:String){
        destinationName= name
        destinationLocation= location
        destinationAddress= address
    }

    fun setOriginCompletely(name:String, location:LatLng, address:String){
        originName=name
        originAddress=address
        originLocation=location
    }

    fun setTransportation(transportation:String){
        transportationMode=transportation
    }

    fun getTransportation():String{
        return transportationMode
    }
}
