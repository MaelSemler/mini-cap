package com.soen390.conumap

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringReader

import android.widget.Button
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        
        // Customise the styling of the map using a JSON object defined in the raw resource file
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle ))
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnInfoWindowClickListener(this)

        addShapesToMap()
        addMarkersToMap()
        setUpMap()
        changeBetweenCampuses(map)
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
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
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

        map.addPolygon(buildingCJ)
        map.addPolygon(buildingGE)
        map.addPolygon(buildingCC)
        map.addPolygon(buildingSP)
        map.addPolygon(buildingRF)
        map.addPolygon(buildingAD)
        map.addPolygon(buildingFC)
        map.addPolygon(buildingVL)
        map.addPolygon(buildingPY)

        // Creates the buildings for SGW campus
        val buildingEV = PolygonOptions()
            .add(
                LatLng(45.495208, -73.577884),
                LatLng(45.495626, -73.578831),
                LatLng(45.495961, -73.578492),
                LatLng(45.495733, -73.578015),
                LatLng(45.496052, -73.577709),
                LatLng(45.495832, -73.577246),
                LatLng(45.495540, -73.577528),
                LatLng(45.495552, -73.577557),
                LatLng(45.495474, -73.577626),
                LatLng(45.495456, -73.577598),
                LatLng(45.495208, -73.577884)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingGM = PolygonOptions()
            .add(
                LatLng(45.495651, -73.578809),
                LatLng(45.495780, -73.579088),
                LatLng(45.495761, -73.579105),
                LatLng(45.495783, -73.579151),
                LatLng(45.496133, -73.578808),
                LatLng(45.495977, -73.578482)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingH  = PolygonOptions()
            .add(
                LatLng(45.496832, -73.578850),
                LatLng(45.497173, -73.579553),
                LatLng(45.497729, -73.579034),
                LatLng(45.497380, -73.5783300),
                LatLng(45.496832, -73.578850)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingFG = PolygonOptions()
            .add(
                LatLng(45.494703, -73.578041),
                LatLng(45.494456, -73.577615),
                LatLng(45.494397, -73.577691),
                LatLng(45.494425, -73.577763),
                LatLng(45.494404, -73.577802),
                LatLng(45.494374, -73.577766),
                LatLng(45.494188, -73.577990),
                LatLng(45.494201, -73.578011),
                LatLng(45.494117, -73.578118),
                LatLng(45.494104, -73.578113),
                LatLng(45.493911, -73.578345),
                LatLng(45.493922, -73.578360),
                LatLng(45.493898, -73.578391),
                LatLng(45.493881, -73.578384),
                LatLng(45.493837, -73.578437),
                LatLng(45.493847, -73.578465),
                LatLng(45.493626, -73.578731),
                LatLng(45.493832, -73.579068),
                LatLng(45.494301, -73.578518),
                LatLng(45.494310, -73.578532),
                LatLng(45.494378, -73.578443),
                LatLng(45.494703, -73.578041)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingMB = PolygonOptions()
            .add(
                LatLng(45.495188, -73.578524),
                LatLng(45.495004, -73.578724),
                LatLng(45.495035, -73.578790),
                LatLng(45.495005, -73.578822),
                LatLng(45.495172, -73.579177),
                LatLng(45.495225, -73.579120),
                LatLng(45.495364, -73.579369),
                LatLng(45.495527, -73.579199),
                LatLng(45.495450, -73.578964),
                LatLng(45.495188, -73.578524)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingLB = PolygonOptions()
            .add(
                LatLng(45.496254, -73.577697),
                LatLng(45.496676, -73.578576),
                LatLng(45.496713, -73.578538),
                LatLng(45.496734, -73.578586),
                LatLng(45.496913, -73.578417),
                LatLng(45.496888, -73.578369),
                LatLng(45.496931, -73.578331),
                LatLng(45.496918, -73.578293),
                LatLng(45.496948, -73.578260),
                LatLng(45.496970, -73.578306),
                LatLng(45.497009, -73.578271),
                LatLng(45.497028, -73.578307),
                LatLng(45.497278, -73.578058),
                LatLng(45.497152, -73.577808),
                LatLng(45.497123, -73.577837),
                LatLng(45.497099, -73.577789),
                LatLng(45.497136, -73.577743),
                LatLng(45.497048, -73.577581),
                LatLng(45.497017, -73.577611),
                LatLng(45.496998, -73.577568),
                LatLng(45.497025, -73.577536),
                LatLng(45.496903, -73.577285),
                LatLng(45.496617, -73.577555),
                LatLng(45.496635, -73.577602),
                LatLng(45.496596, -73.577637),
                LatLng(45.496508, -73.577458),
                LatLng(45.496254, -73.577697)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        val buildingGN = PolygonOptions()
            .add(
                LatLng(45.492593, -73.576533),
                LatLng(45.492617, -73.576577),
                LatLng(45.492609, -73.576584),
                LatLng(45.492747, -73.576869),
                LatLng(45.492728, -73.576874),
                LatLng(45.492678, -73.576923),
                LatLng(45.492678, -73.576965),
                LatLng(45.492712, -73.577033),
                LatLng(45.492774, -73.576973),
                LatLng(45.492796, -73.576978),
                LatLng(45.492810, -73.576960),
                LatLng(45.492897, -73.577142),
                LatLng(45.492838, -73.577198),
                LatLng(45.492927, -73.577380),
                LatLng(45.492993, -73.577314),
                LatLng(45.493089, -73.577512),
                LatLng(45.493198, -73.577402),
                LatLng(45.493108, -73.577214),
                LatLng(45.493364, -73.576962),
                LatLng(45.493353, -73.576939),
                LatLng(45.493441, -73.576854),
                LatLng(45.493479, -73.576934),
                LatLng(45.493450, -73.576964),
                LatLng(45.493530, -73.577127),
                LatLng(45.493576, -73.577082),
                LatLng(45.493601, -73.577134),
                LatLng(45.493581, -73.577153),
                LatLng(45.493581, -73.577153),
                LatLng(45.493633, -73.577266),
                LatLng(45.493747, -73.577157),
                LatLng(45.493671, -73.577004),
                LatLng(45.493727, -73.576947),
                LatLng(45.493665, -73.576825),
                LatLng(45.493615, -73.576875),
                LatLng(45.493554, -73.576746),
                LatLng(45.493898, -73.576408),
                LatLng(45.494193, -73.577022),
                LatLng(45.493869, -73.577336),
                LatLng(45.493973, -73.577561),
                LatLng(45.494124, -73.577411),
                LatLng(45.494093, -73.577348),
                LatLng(45.494391, -73.577056),
                LatLng(45.494019, -73.576281),
                LatLng(45.494121, -73.576177),
                LatLng(45.494034, -73.575996),
                LatLng(45.493932, -73.576098),
                LatLng(45.493713, -73.575641),
                LatLng(45.493570, -73.575782),
                LatLng(45.493794, -73.576246),
                LatLng(45.493493, -73.576541),
                LatLng(45.493470, -73.576499),
                LatLng(45.493341, -73.576625),
                LatLng(45.493025, -73.577006),
                LatLng(45.492940, -73.576833),
                LatLng(45.492948, -73.576799),
                LatLng(45.492926, -73.576746),
                LatLng(45.492927, -73.576749),
                LatLng(45.492898, -73.576745),
                LatLng(45.492732, -73.576396),
                LatLng(45.492593, -73.576533)
            ).fillColor(concordiaRed).strokeWidth(0.1f)

        val buildingFB = PolygonOptions()
            .add(
                LatLng(45.494397, -73.577521),
                LatLng(45.494700, -73.578035),
                LatLng(45.494911, -73.577788),
                LatLng(45.494870, -73.577714),
                LatLng(45.494877, -73.577705),
                LatLng(45.494836, -73.577633),
                LatLng(45.494844, -73.577627),
                LatLng(45.494798, -73.577550),
                LatLng(45.494764, -73.577468),
                LatLng(45.494764, -73.577467),
                LatLng(45.494777, -73.577456),
                LatLng(45.494692, -73.577310),
                LatLng(45.494700, -73.577298),
                LatLng(45.494656, -73.577218),
                LatLng(45.494397, -73.577521)
            ).fillColor(concordiaRed).strokeWidth(0.1f)
        map.addPolygon(buildingEV)
        map.addPolygon(buildingGM)
        map.addPolygon(buildingH)
        map.addPolygon(buildingFG)
        map.addPolygon(buildingMB)
        map.addPolygon(buildingLB)
        map.addPolygon(buildingGN)
        map.addPolygon(buildingFB)
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
