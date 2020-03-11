package com.soen390.conumap

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions

import android.widget.Button
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {
    // For locating user.
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    // Locations of campus buildings and info.
    // SGW buildings.
    private val sgwHLocation = LatLng(45.497390, -73.578859)
    private val sgwHName = "Henry F. Hall Building"
    private val sgwHInfo = "SGW Campus\n1455 De Maisonneuve Blvd. W."
    private val sgwGMLocation = LatLng(45.496002, -73.578490)
    private val sgwGMName = "Guy-De Maisonneuve Building"
    private val sgwGMInfo = "SGW Campus\n1550 De Maisonneuve Blvd. W."
    private val sgwMBLocation = LatLng(45.495418, -73.579169)
    private val sgwMBName = "John Molson Building"
    private val sgwMBInfo = "SGW Campus\n1450 Guy"
    private val sgwEVLocation = LatLng(45.495506, -73.577774)
    private val sgwEVName = "Engineering, Computer Science and Visual Arts Integrated Complex"
    private val sgwEVInfo = "SGW Campus\n1515 St. Catherine W."
    private val sgwFGLocation = LatLng(45.494373, -73.578332)
    private val sgwFGName = "Faubourg Ste-Catherine Building"
    private val sgwFGInfo = "SGW Campus\n1610 St. Catherine W."
    private val sgwFBLocation = LatLng(45.494753, -73.577731)
    private val sgwFBName = "Faubourg Building"
    private val sgwFBInfo = "SGW Campus\n1250 Guy"
    private val sgwLBLocation = LatLng(45.496990, -73.577951)
    private val sgwLBName = "J.W. McConnel Building"
    private val sgwLBInfo = "SGW Campus\n1400 De Maisonneuve Blvd. W."
    private val sgwGNLocation = LatLng(45.493652, -73.576985)
    private val sgwGNName = "Grey Nuns Building"
    private val sgwGNInfo = "SGW Campus\n1190 Guy"
    private val sgwLSLocation = LatLng(45.496232, -73.579491)
    private val sgwLSName = "Learning Square"
    private val sgwLSInfo = "SGW Campus\n1535 De Maisonneuve Blvd. W."
    private val sgwVALocation = LatLng(45.495683, -73.573565)
    private val sgwVAName = "Visual Arts Building"
    private val sgwVAInfo = "SGW Campus\n1395 René Lévesque W."
    // LOY buildings.
    private val loyGELocation = LatLng(45.456984, -73.640442)
    private val loyGEName = "Centre for Structural and Functional Genomics"
    private val loyGEInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyCJLocation = LatLng(45.457477, -73.640306)
    private val loyCJName = "Communication Studies and Journalism Building"
    private val loyCJInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyADLocation = LatLng(45.457973, -73.639890)
    private val loyADName = "Administration Building"
    private val loyADInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loySPLocation = LatLng(45.457879, -73.641682)
    private val loySPName = "Richard J. Renaud Science Complex"
    private val loySPInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyCCLocation = LatLng(45.458266, -73.640282)
    private val loyCCName = "Central Building"
    private val loyCCInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyFCLocation = LatLng(45.458564, -73.639295)
    private val loyFCName = "F. C. Smith Building"
    private val loyFCInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyVLLocation = LatLng(45.458982, -73.638619)
    private val loyVLName = "Vanier Library Building"
    private val loyVLInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loySCLocation = LatLng(45.459085, -73.639221)
    private val loySCName = "Student Centre"
    private val loySCInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyPTLocation = LatLng(45.459325, -73.638907)
    private val loyPTName = "Oscar Peterson Concert Hall"
    private val loyPTInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyPSLocation = LatLng(45.459720, -73.639819)
    private val loyPSName = "Physical Services Building"
    private val loyPSInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyPYLocation = LatLng(45.459028, -73.640591)
    private val loyPYName = "Psychology Building"
    private val loyPYInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyHALocation = LatLng(45.459431, -73.641248)
    private val loyHAName = "Hingston Hall, wing HA"
    private val loyHAInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyHBLocation = LatLng(45.459081, -73.641940)
    private val loyHBName = "Hingston Hall, wing HB"
    private val loyHBInfo = "Loyola Campus\n7141 Sherbrooke W."
    private val loyHCLocation = LatLng(45.459630, -73.642082)
    private val loyHCName = "Hingston Hall, wing HC"
    private val loyHCInfo = "Loyola Campus\n7141 Sherbrooke W."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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
            addMarkersToMap()
            setUpMap()
            currentLocationButton()
            changeBetweenCampuses(map)
        }
    }

    private fun addMarkersToMap() {
        // SGW buildings.
        map.addMarker(MarkerOptions()
            .position(sgwHLocation)
            .alpha(0.0F)
            .title(sgwHName)
            .snippet(sgwHInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwGMLocation)
            .alpha(0.0F)
            .title(sgwGMName)
            .snippet(sgwGMInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwMBLocation)
            .alpha(0.0F)
            .title(sgwMBName)
            .snippet(sgwMBInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwEVLocation)
            .alpha(0.0F)
            .title(sgwEVName)
            .snippet(sgwEVInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwFGLocation)
            .alpha(0.0F)
            .title(sgwFGName)
            .snippet(sgwFGInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwFBLocation)
            .alpha(0.0F)
            .title(sgwFBName)
            .snippet(sgwFBInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwLBLocation)
            .alpha(0.0F)
            .title(sgwLBName)
            .snippet(sgwLBInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwGNLocation)
            .alpha(0.0F)
            .title(sgwGNName)
            .snippet(sgwGNInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwLSLocation)
            .alpha(0.0F)
            .title(sgwLSName)
            .snippet(sgwLSInfo)
        )

        map.addMarker(MarkerOptions()
            .position(sgwVALocation)
            .alpha(0.0F)
            .title(sgwVAName)
            .snippet(sgwVAInfo)
        )

        // LOY buildings.
        map.addMarker(MarkerOptions()
            .position(loyGELocation)
            .alpha(0.0F)
            .title(loyGEName)
            .snippet(loyGEInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyCJLocation)
            .alpha(0.0F)
            .title(loyCJName)
            .snippet(loyCJInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyADLocation)
            .alpha(0.0F)
            .title(loyADName)
            .snippet(loyADInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loySPLocation)
            .alpha(0.0F)
            .title(loySPName)
            .snippet(loySPInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyCCLocation)
            .alpha(0.0F)
            .title(loyCCName)
            .snippet(loyCCInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyFCLocation)
            .alpha(0.0F)
            .title(loyFCName)
            .snippet(loyFCInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyVLLocation)
            .alpha(0.0F)
            .title(loyVLName)
            .snippet(loyVLInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loySCLocation)
            .alpha(0.0F)
            .title(loySCName)
            .snippet(loySCInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyPTLocation)
            .alpha(0.0F)
            .title(loyPTName)
            .snippet(loyPTInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyPSLocation)
            .alpha(0.0F)
            .title(loyPSName)
            .snippet(loyPSInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyPYLocation)
            .alpha(0.0F)
            .title(loyPYName)
            .snippet(loyPYInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyHALocation)
            .alpha(0.0F)
            .title(loyHAName)
            .snippet(loyHAInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyHBLocation)
            .alpha(0.0F)
            .title(loyHBName)
            .snippet(loyHBInfo)
        )

        map.addMarker(MarkerOptions()
            .position(loyHCLocation)
            .alpha(0.0F)
            .title(loyHCName)
            .snippet(loyHCInfo)
        )
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

    private fun addShapesToMap(){
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
        val buildingFBArray = resources.getStringArray(R.array.buildingGNPoints)
        for (i in buildingFBArray.indices) {
            if (i % 2 == 0) {
                var latitude = buildingGNArray[i].toDouble()
                var longitude = buildingGNArray[i + 1].toDouble()
                var latlng = LatLng(latitude, longitude)
                buildingFB.add(latlng)
            }
        }
        buildingFB.fillColor(concordiaRed).strokeWidth(0.1f)

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
        map.addPolygon(buildingEV)
        map.addPolygon(buildingGM)
        map.addPolygon(buildingH)
        map.addPolygon(buildingFG)
        map.addPolygon(buildingMB)
        map.addPolygon(buildingLB)
        map.addPolygon(buildingGN)
        map.addPolygon(buildingFB)
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


    private fun changeBetweenCampuses (googleMap:GoogleMap) {
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
            addMarkersToMap()
            map.moveCamera(CameraUpdateFactory.newLatLng(downTown))
            addShapesToMap()
        }
        buttonLOY?.setOnClickListener()
        {
            map.clear()
            map.addMarker(MarkerOptions().position(loyola).title("LOY").icon(BitmapDescriptorFactory.defaultMarker(342.toFloat()))) //sets color, title and position of marker
            addMarkersToMap()
            map.moveCamera(CameraUpdateFactory.newLatLng(loyola))
            addShapesToMap()
        }
    }

    // Default marker behaviour (show popup).
    override fun onMarkerClick(mkr: Marker?) = false

    // Close info window when it is tapped.
    override fun onInfoWindowClick(mkr: Marker) {
        mkr.hideInfoWindow()
    }
}
