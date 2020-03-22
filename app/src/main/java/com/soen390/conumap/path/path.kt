package com.soen390.conumap.path

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.Directions.directions


//TODO: Implement Live Data Binding as in directions.kt
object path {
    //origin
   // val destination= Location()
    private var distance=0.0
    private var transportationMethod="Car"
    //time
    //accessibilityFriendly

    init
    {
        //TODO: Initialize once data binding is done
    }

    fun switchOriginAndDestination()
    {
        //TODO: To be implemented
    }

    fun getAlternatives()
    {
        //TODO: To be implemented
    }

}