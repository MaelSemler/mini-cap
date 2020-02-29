package com.soen390.conumap

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private  const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) {
            location->
            if(location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        setUpMap()
        //TODO: Get Origin and Get Destination coordinates
//        val originLatLng = getOrigin()
//        val destinationLatLng = getDestination
        val originLatLng = LatLng(45.502516, -73.563929)//HardCoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now
        //TODO: Origin and Destination should have a title
            this.mMap!!.addMarker(MarkerOptions().position(originLatLng).title("This is the origin"))
            this.mMap!!.addMarker(MarkerOptions().position(destinationLatLng).title("This is the destination"))
            this.mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 14.5f))

        route(originLatLng, destinationLatLng)
    }

    private fun route(originLatLng: LatLng, destinationLatLng:LatLng){
        val path : MutableList<List<LatLng>> = ArrayList()
        val urlDirections= getString(R.string.DirectionAPI) +"origin=" + originLatLng.latitude + "," + originLatLng.longitude +"&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + getString(R.string.apiKey)

        val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, com.android.volley.Response.Listener<String> {
                response ->
            val jsonResponse = JSONObject(response)
            // Get routes
            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")
            for (i in 0 until steps.length()) {
                val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                path.add(PolyUtil.decode(points))
            }
            for (i in 0 until path.size) {
                this.mMap!!.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
            }
        }, com.android.volley.Response.ErrorListener {
                _ ->
        }){}
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(directionsRequest)
    }



    // For sample unit tests, remove for sprint 2.
    fun sum(a: Int, b: Int) = a + b

    fun square(num: Double) = num * num

    fun reverse(word: String) = word.reversed()

    fun canYouDrink(age: Int): Boolean {
        return age >= 18
    }

    override fun onMarkerClick(p0: Marker?)= false
}
