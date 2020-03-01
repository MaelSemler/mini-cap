package com.soen390.conumap

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.widget.Button
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
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
        val button = findViewById<Button>(R.id.button_SGW)
        val button2= findViewById<Button>(R.id.button_LOY)
        val loyola=LatLng(45.458275,-73.640469)
        val downTown=LatLng(45.4975,-73.579004)
        button?.setOnClickListener()
        {
            map.clear()
            map.addMarker(MarkerOptions().
                    position(downTown).
                title("SGW").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            map.moveCamera(CameraUpdateFactory.newLatLng(downTown))

        }
        button2?.setOnClickListener()
        {
            map.clear()
            map.addMarker(MarkerOptions().position(loyola).title("LOY").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            map.moveCamera(CameraUpdateFactory.newLatLng(loyola))
        }
    }
    // For sample unit tests, remove for sprint 2.
    fun sum(a: Int, b: Int) = a + b

    fun square(num: Double) = num * num

    fun reverse(word: String) = word.reversed()

    fun canYouDrink(age: Int): Boolean {
        return age >= 18
    }
}
