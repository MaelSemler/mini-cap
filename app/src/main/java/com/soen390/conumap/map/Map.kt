package com.soen390.conumap.map

import android.location.Location
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.soen390.conumap.permission.Permission

object Map {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    fun getMap(): Map {
        return this
    }

    //This is function is called from MapFragment when the Map has loaded.
    //It sets all the default stuff for the map, like permission, centering on location, etc.
    fun setUpMap(map: GoogleMap, activity: FragmentActivity){

        //Checks the permissions and ask the user if the app does not have the permission to use the localisation feature
        if(!Permission.checkPermission(activity!!)){
            Permission.requestPermission(activity!!, LOCATION_PERMISSION_REQUEST_CODE)
        }
        uiSettings(map)
        map.isMyLocationEnabled = true
        var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        centerMapOnUserLocation(map, activity, fusedLocationClient)

    }

    fun centerMapOnUserLocation(map: GoogleMap, activity: FragmentActivity, fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener(activity) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))
            }
        }
    }

    private fun uiSettings(map: GoogleMap){
        // Hide toolbar to open destination in Google Maps externally.
        map.uiSettings.isMapToolbarEnabled = false

    }
}