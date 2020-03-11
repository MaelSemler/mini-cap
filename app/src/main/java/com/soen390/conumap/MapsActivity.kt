package com.soen390.conumap

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.graphics.Color
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import androidx.fragment.app.Fragment

import android.widget.Button
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {
    // For locating user.
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()

        /*
        Following line on 'SearchPlaceFragment()' to be uncommented to see empty search bar
         */
        val searchBarFragment = SearchPlaceFragment()

        /*
         Following line on 'SearchPlaceCompletedFragment()' to be uncommented to see full search bar
         */
        // val searchBarFragment = SearchPlaceCompletedFragment()

        // Replace the fragment on container
        ft.replace(R.id.frame_container,searchBarFragment)
        ft.addToBackStack(null)

        ft.commit()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
      
        if(map != null){
            // Customise the styling of the map using a JSON object defined in the raw resource file
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle ))
            map.uiSettings.isMyLocationButtonEnabled = false
            map.setOnInfoWindowClickListener(this)

            addShapesToMap()
            createBuildings()
            setUpMap()
            currentLocationButton()
            changeBetweenCampuses(map)
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }  
      
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // Hide toolbar to open destination in Google Maps externally.
        map.uiSettings.isMapToolbarEnabled = false

        // Change to show building info on tap.
        map.setInfoWindowAdapter(BuildingInfoWindowAdapter(this))

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))
            }
        }
    }

    // Adds Building objects for all campus buildings.
    private fun createBuildings() {
        // SGW Buildings.
        var sgwH = Building(
            resources.getString(R.string.sgwHName),
            resources.getString(R.string.sgwHInfo),
            LatLng(45.497304, -73.578923),
            map
        )

        val sgwGM = Building(
            resources.getString(R.string.sgwGMName),
            resources.getString(R.string.sgwGMInfo),
            LatLng(45.495850, -73.578766),
            map
        )
        val sgwMB = Building(
            resources.getString(R.string.sgwMBName),
            resources.getString(R.string.sgwMBInfo),
            LatLng(45.495418, -73.579169),
            map
        )
        val sgwEV = Building(
            resources.getString(R.string.sgwEVName),
            resources.getString(R.string.sgwEVInfo),
            LatLng(45.495506, -73.577774),
            map
        )
        val sgwFG = Building(
            resources.getString(R.string.sgwFGName),
            resources.getString(R.string.sgwFGInfo),
            LatLng(45.494373, -73.578332),
            map
        )
        val sgwFB = Building(
            resources.getString(R.string.sgwFBName),
            resources.getString(R.string.sgwFBInfo),
            LatLng(45.494753, -73.577731),
            map
        )
        val sgwLB = Building(
            resources.getString(R.string.sgwLBName),
            resources.getString(R.string.sgwLBInfo),
            LatLng(45.496990, -73.577951),
            map
        )
        val sgwGN = Building(
            resources.getString(R.string.sgwGNName),
            resources.getString(R.string.sgwGNInfo),
            LatLng(45.493652, -73.576985),
            map
        )
        val sgwLS = Building(
            resources.getString(R.string.sgwLSName),
            resources.getString(R.string.sgwLSInfo),
            LatLng(45.496232, -73.579491),
            map
        )
        val sgwVA = Building(
            resources.getString(R.string.sgwVAName),
            resources.getString(R.string.sgwVAInfo),
            LatLng(45.495683, -73.573565),
            map
        )

        // LOY Buildings.
        val loyGE = Building(
            resources.getString(R.string.loyGEName),
            resources.getString(R.string.loyGEInfo),
            LatLng(45.456984, -73.640442),
            map
        )
        val loyCJ = Building(
            resources.getString(R.string.loyCJName),
            resources.getString(R.string.loyCJInfo),
            LatLng(45.457477, -73.640306),
            map
        )
        val loyAD = Building(
            resources.getString(R.string.loyADName),
            resources.getString(R.string.loyADInfo),
            LatLng(45.457973, -73.639890),
            map
        )
        val loySP = Building(
            resources.getString(R.string.loySPName),
            resources.getString(R.string.loySPInfo),
            LatLng(45.457879, -73.641682),
            map
        )
        val loyCC = Building(
            resources.getString(R.string.loyCCName),
            resources.getString(R.string.loyCCInfo),
            LatLng(45.458266, -73.640282),
            map
        )
        val loyFC = Building(
            resources.getString(R.string.loyFCName),
            resources.getString(R.string.loyFCInfo),
            LatLng(45.458564, -73.639295),
            map
        )
        val loyVL = Building(
            resources.getString(R.string.loyVLName),
            resources.getString(R.string.loyVLInfo),
            LatLng(45.458982, -73.638619),
            map
        )
        val loySC = Building(
            resources.getString(R.string.loySCName),
            resources.getString(R.string.loySCInfo),
            LatLng(45.459085, -73.639221),
            map
        )
        val loyPT = Building(
            resources.getString(R.string.loyPTName),
            resources.getString(R.string.loyPTInfo),
            LatLng(45.459325, -73.638907),
            map
        )
        val loyPS = Building(
            resources.getString(R.string.loyPSName),
            resources.getString(R.string.loyPSInfo),
            LatLng(45.459720, -73.639819),
            map
        )
        val loyPY = Building(
            resources.getString(R.string.loyPYName),
            resources.getString(R.string.loyPYInfo),
            LatLng(45.459028, -73.640591),
            map
        )
        val loyHA = Building(
            resources.getString(R.string.loyHAName),
            resources.getString(R.string.loyHAInfo),
            LatLng(45.459431, -73.641248),
            map
        )
        val loyHB = Building(
            resources.getString(R.string.loyHBName),
            resources.getString(R.string.loyHBInfo),
            LatLng(45.459081, -73.641940),
            map
        )
        val loyHC = Building(
            resources.getString(R.string.loyHCName),
            resources.getString(R.string.loyHCInfo),
            LatLng(45.459630, -73.642082),
            map
        )
    }

    private fun addShapesToMap() {
        val concordiaRed = Color.rgb(147,35,57)
        // Creates the shapes for Loyola Buildings

        val buildingCJ = PolygonOptions()
        val buildingCJArray = resources.getStringArray(R.array.buildingCJPoints)
        for (i in buildingCJArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingCJArray[i].toDouble()
                var longitude = buildingCJArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingCJ.add(latlng)
            }
        }
        buildingCJ.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingGE = PolygonOptions()
        val buildingGEArray = resources.getStringArray(R.array.buildingGEPoints)
        for (i in buildingGEArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingGEArray[i].toDouble()
                var longitude = buildingGEArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingGE.add(latlng)
            }
        }
        buildingGE.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingCC = PolygonOptions()
        val buildingCCArray = resources.getStringArray(R.array.buildingCCPoints)
        for (i in buildingCCArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingCCArray[i].toDouble()
                var longitude = buildingCCArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingCC.add(latlng)
            }
        }
        buildingCC.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingSP = PolygonOptions()
        val buildingSPArray = resources.getStringArray(R.array.buildingSPPoints)
        for (i in buildingSPArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingSPArray[i].toDouble()
                var longitude = buildingSPArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingSP.add(latlng)
            }
        }
        buildingSP.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingRF = PolygonOptions()
        val buildingRFArray = resources.getStringArray(R.array.buildingRFPoints)
        for (i in buildingRFArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingRFArray[i].toDouble()
                var longitude = buildingRFArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingRF.add(latlng)
            }
        }
        buildingRF.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingAD = PolygonOptions()
        val buildingADArray = resources.getStringArray(R.array.buildingADPoints)
        for (i in buildingADArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingADArray[i].toDouble()
                var longitude = buildingADArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingAD.add(latlng)
            }
        }
        buildingAD.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingFC = PolygonOptions()
        val buildingFCArray = resources.getStringArray(R.array.buildingFCPoints)
        for (i in buildingFCArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingFCArray[i].toDouble()
                var longitude = buildingFCArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingFC.add(latlng)
            }
        }
        buildingFC.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingVL = PolygonOptions()
        val buildingVLArray = resources.getStringArray(R.array.buildingVLPoints)
        for (i in buildingVLArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingVLArray[i].toDouble()
                var longitude = buildingVLArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingVL.add(latlng)
            }
        }
        buildingVL.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingPY = PolygonOptions()
        val buildingPYArray = resources.getStringArray(R.array.buildingPYPoints)
        for (i in buildingPYArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingPYArray[i].toDouble()
                var longitude = buildingPYArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingPY.add(latlng)
            }
        }
        buildingPY.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingHA = PolygonOptions()
        val buildingHAArray = resources.getStringArray(R.array.buildingHAPoints)
        for (i in buildingHAArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingHAArray[i].toDouble()
                var longitude = buildingHAArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingHA.add(latlng)
            }
        }
        buildingHA.fillColor(concordiaRed).strokeWidth(0.1f)

        // Creates the buildings for SGW campus
        val buildingEV = PolygonOptions()
        val buildingEVArray = resources.getStringArray(R.array.buildingEVPoints)
        for (i in buildingEVArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingEVArray[i].toDouble()
                var longitude = buildingEVArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingEV.add(latlng)
            }
        }
        buildingEV.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingGM = PolygonOptions()
        val buildingGMArray = resources.getStringArray(R.array.buildingGMPoints)
        for (i in buildingGMArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingGMArray[i].toDouble()
                var longitude = buildingGMArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingGM.add(latlng)
            }
        }
        buildingGM.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingH = PolygonOptions()
        val buildingHArray = resources.getStringArray(R.array.buildingHPoints)
        for (i in buildingHArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingHArray[i].toDouble()
                var longitude = buildingHArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingH.add(latlng)
            }
        }
        buildingH.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingFG = PolygonOptions()
        val buildingFGArray = resources.getStringArray(R.array.buildingFGPoints)
        for (i in buildingFGArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingFGArray[i].toDouble()
                var longitude = buildingFGArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingFG.add(latlng)
            }
        }
        buildingFG.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingMB = PolygonOptions()
        val buildingMBArray = resources.getStringArray(R.array.buildingMBPoints)
        for (i in buildingMBArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingMBArray[i].toDouble()
                var longitude = buildingMBArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingMB.add(latlng)
            }
        }
        buildingMB.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingLB = PolygonOptions()
        val buildingLBArray = resources.getStringArray(R.array.buildingLBPoints)
        for (i in buildingLBArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingLBArray[i].toDouble()
                var longitude = buildingLBArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingLB.add(latlng)
            }
        }
        buildingLB.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingGN = PolygonOptions()
        val buildingGNArray = resources.getStringArray(R.array.buildingGNPoints)
        for (i in buildingGNArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingGNArray[i].toDouble()
                var longitude = buildingGNArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingGN.add(latlng)
            }
        }
        buildingGN.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingFB = PolygonOptions()
        val buildingFBArray = resources.getStringArray(R.array.buildingFBPoints)
        for (i in buildingFBArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingFBArray[i].toDouble()
                var longitude = buildingFBArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingFB.add(latlng)
            }
        }
        buildingFB.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingLS = PolygonOptions()
        val buildingLSArray = resources.getStringArray(R.array.buildingLSPoints)
        for (i in buildingLSArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingLSArray[i].toDouble()
                var longitude = buildingLSArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingLS.add(latlng)
            }
        }
        buildingLS.fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingVA = PolygonOptions()
        val buildingVAArray = resources.getStringArray(R.array.buildingVAPoints)
        for (i in buildingVAArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingVAArray[i].toDouble()
                var longitude = buildingVAArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingVA.add(latlng)
            }
        }
        buildingVA.fillColor(concordiaRed).strokeWidth(0.1f)

        // Adds all buildings to Map
        map.addPolygon(buildingCJ)
        map.addPolygon(buildingGE)
        map.addPolygon(buildingCC)
        map.addPolygon(buildingSP)
        map.addPolygon(buildingRF)
        map.addPolygon(buildingAD)
        map.addPolygon(buildingFC)
        map.addPolygon(buildingVL)
        map.addPolygon(buildingPY)
        map.addPolygon(buildingHA)
        map.addPolygon(buildingEV)
        map.addPolygon(buildingGM)
        map.addPolygon(buildingH)
        map.addPolygon(buildingFG)
        map.addPolygon(buildingMB)
        map.addPolygon(buildingLB)
        map.addPolygon(buildingGN)
        map.addPolygon(buildingFB)
        map.addPolygon(buildingLS)
        map.addPolygon(buildingVA)
    }

    //Listener for the current location button
    private fun currentLocationButton (){
        val buttonSGW = findViewById<Button>(R.id.button_current_location) //finds the button
        buttonSGW?.setOnClickListener()
        {
            val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude) //Get the latitude and longitude from the lastLocation of the user
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f)) //Move the camera to the user's location
        }
    }

    private fun changeBetweenCampuses (googleMap:GoogleMap) {   //Button to toggle between campuses
        //When either button is clicked, map moves to respective location.
        val buttonSGW = findViewById<Button>(R.id.button_SGW)
        val buttonLOY= findViewById<Button>(R.id.button_LOY)

        val loyola=LatLng((resources.getString(R.string.LoyLat)).toDouble(),(resources.getString(R.string.LOYLong)).toDouble())
        //creates coordinates for loyola campus
        val downTown=LatLng((resources.getString(R.string.SGWLat)).toDouble(),(resources.getString(R.string.SGWLong)).toDouble())
        //creates coordinates for downtown campus

        buttonSGW?.setOnClickListener()   //function for when SGW button is clicked
        {
            map.clear()
            map.addMarker(MarkerOptions().
                    position(downTown).
                title("SGW").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            createBuildings()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(downTown,18f)) //changed from move camera
            addShapesToMap()
        }
        buttonLOY?.setOnClickListener()     //function for when Loyola button is clicked
        {
            map.clear()
            map.addMarker(MarkerOptions().position(loyola).title("LOY").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            createBuildings()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loyola,18f)) ///changed from move camera
            addShapesToMap()
        }
    }

    // Default marker behaviour (show popup).
    override fun onMarkerClick(mkr: Marker) = false

    // Close info window when it is tapped.
    override fun onInfoWindowClick(mkr: Marker) {
        mkr.hideInfoWindow()
    }
}
