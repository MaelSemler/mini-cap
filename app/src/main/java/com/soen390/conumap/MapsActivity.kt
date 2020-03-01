package com.soen390.conumap

import android.content.pm.PackageManager
import com.google.android.gms.maps.model.*
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.res.Resources
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.PolyUtil
import org.json.JSONArray
import org.json.JSONObject


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private  const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val routeButton: Button = findViewById(R.id.route_button)
        routeButton.setOnClickListener{ routeTest()}

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
        map = googleMap
        
        // Customise the styling of the map using a JSON object defined in the raw resource file
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle ))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
                setUpMap()
        changeBetweenCampuses(map)
    }


    fun routeTest(){
        val originLatLng = LatLng(45.502516, -73.563929)//HardCoded for now
        val destinationLatLng = LatLng(45.497044, -73.578407)//HardCoded for now
        //TODO: Origin and Destination should have a title
        this.map!!.addMarker(MarkerOptions().position(originLatLng).title("This is the origin"))
        this.map!!.addMarker(MarkerOptions().position(destinationLatLng).title("This is the destination"))
        this.map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 14.5f))

        route(originLatLng, destinationLatLng)
    }

    private fun route(originLatLng: LatLng, destinationLatLng:LatLng){
        val path : MutableList<List<LatLng>> = ArrayList()
        val urlDirections= getString(R.string.DirectionAPI) +"origin=" + originLatLng.latitude + "," + originLatLng.longitude +"&destination=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "&key=" + getString(R.string.apiKey)

        val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, com.android.volley.Response.Listener<String> {
                response ->
            val jsonResponse = JSONObject(response)
            // Get routes
            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")

            //TODO: Create function to clean up the directions
            extractDirections(steps)

            for (i in 0 until steps.length()) {
                val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                path.add(PolyUtil.decode(points))
            }
            for (i in 0 until path.size) {
                this.map!!.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
            }
        }, com.android.volley.Response.ErrorListener {
                _ ->
        }){}
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(directionsRequest)
    }

    fun extractDirections(steps: JSONArray) {
        val directionText : TextView = findViewById(R.id.Directions)
        var textConverted="Direction:" + '\n'
        for (i in 0 until steps.length()){
            textConverted+= (i+1).toString() +". " +  steps.getJSONObject(i).getString("html_instructions") + '\n'
        }
        directionText.text= textConverted
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // 1
        map.isMyLocationEnabled = true

        // 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    fun changeBetweenCampuses (googleMap:GoogleMap){
        //When either button is clicked, map moves to respective location.
        map = googleMap
        val buttonSGW = findViewById<Button>(R.id.button_SGW)
        val buttonLOY= findViewById<Button>(R.id.button_LOY)
        val loyola=LatLng(45.458275,-73.640469)
        val downTown=LatLng(45.4975,-73.579004)
        buttonSGW?.setOnClickListener()
        {
            map.clear()
            map.addMarker(MarkerOptions().
                    position(downTown).
                title("SGW").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            map.moveCamera(CameraUpdateFactory.newLatLng(downTown))

        }
        buttonLOY?.setOnClickListener()
        {
            map.clear()
            map.addMarker(MarkerOptions().position(loyola).title("LOY").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            map.moveCamera(CameraUpdateFactory.newLatLng(loyola))
        }
    }
}
    // For sample unit tests, remove for sprint 2.
    fun sum(a: Int, b: Int) = a + b

    fun square(num: Double) = num * num

    fun reverse(word: String) = word.reversed()

    fun canYouDrink(age: Int): Boolean {
        return age >= 18
    }



