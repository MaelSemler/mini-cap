package com.soen390.conumap.map

import android.graphics.Color.rgb
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.soen390.conumap.R
import com.soen390.conumap.building.Building
import com.soen390.conumap.building.BuildingCreator
import com.soen390.conumap.permission.Permission

object Map: GoogleMap.OnPolygonClickListener{

    private const val LOCATION_PERMISSION_REQUEST_CODE = 1 //The constant for the permission code
    private var lastLocation: LatLng = LatLng(45.497304, -73.578923) //This is the last location of the user
    private lateinit var gMap: GoogleMap
    private var buildings: ArrayList<Building> = arrayListOf()

    //This is function is called from MapFragment when the Map has loaded.
    //It sets all the default stuff for the map, like permission, centering on location, etc.
    fun setUpMap(googleMap: GoogleMap, activity: FragmentActivity){

        gMap = googleMap
        gMap.setOnPolygonClickListener(this)

        //Checks the permissions and ask the user if the app does not have the permission to use the localisation feature
        if(!Permission.checkPermission(activity)){
            Permission.requestPermission(activity, LOCATION_PERMISSION_REQUEST_CODE)
        }

        //Calls the uiSettings function to set the defaults for the map
        uiSettings(activity)
        gMap.isMyLocationEnabled = true //Makes sure the mylocation  is enabled
        var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity) //Create the fusedLocation
        centerMapOnUserLocation(activity, fusedLocationClient) //Center the map on the user

        buildings = BuildingCreator.createBuildings(gMap)
        BuildingCreator.createPolygons(gMap)
    }

    fun getMapInstance(): GoogleMap{
        return gMap
    }

    //This sets the UI settings for the map
    private fun uiSettings(activity: FragmentActivity){

        // Hide toolbar to open destination in Google Maps externally.
        gMap.uiSettings.isMapToolbarEnabled = false

        // Customise the styling of the map using a JSON object defined in the raw resource file
        gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.mapstyle ))

        //Remove the location button given by Google
        gMap.uiSettings.isMyLocationButtonEnabled = false

    }

    //This method centers the map on the user's current location
    fun centerMapOnUserLocation(activity: FragmentActivity, fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.lastLocation.addOnSuccessListener(activity) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = LatLng(location.latitude, location.longitude)
                moveCamera(lastLocation, 18f)
            }
        }
    }

    //This animate the camera to the given LatLng with the given zoom on the map
    fun moveCamera(position: LatLng, zoom: Float){
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom))
    }

    //This adds a marker to the map, with the given position and title
    fun addMarker(position: LatLng, title: String) {
        gMap.addMarker(MarkerOptions().position(position).title(title).icon(BitmapDescriptorFactory.defaultMarker(342.toFloat())))
    }

    override fun onPolygonClick(p0: Polygon?) {
        //Search in the Building array to see which Building has been clicked, depending on the polygon ID (zIndex)
        buildings.forEach {
            if(p0?.zIndex == it.polygonID){ //We  know this is the building that has been clicked
                it.marker.showInfoWindow()

            }
        }
    }


}