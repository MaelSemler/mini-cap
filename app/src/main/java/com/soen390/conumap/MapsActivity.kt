package com.soen390.conumap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringReader


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val concordiaRed = Color.rgb(147,35,57)

        // Creates the shapes for Loyola Buildings
        val buildingCJ = PolygonOptions()
            .add(
                LatLng(45.457218, -73.640016),
                LatLng(45.457307, -73.640074),
                LatLng(45.457311, -73.640049),
                LatLng(45.457361, -73.640075),
                LatLng(45.457412, -73.640207), //
                LatLng(45.457179, -73.640391),//
                LatLng(45.457280, -73.640659),
                LatLng(45.457304, -73.640639),
                LatLng(45.457335, -73.640717),
                LatLng(45.457598, -73.640503),
                LatLng(45.457651, -73.640632),
                LatLng(45.457830, -73.640484),
                LatLng(45.457755, -73.640292),
                LatLng(45.457726, -73.640315),
                LatLng(45.457622, -73.640045),
                LatLng(45.457486, -73.640152),
                LatLng(45.457434, -73.640028),
                LatLng(45.457445, -73.639945),
                LatLng(45.457464, -73.639955),
                LatLng(45.457480, -73.639823),
                LatLng(45.457429, -73.639771),
                LatLng(45.457400, -73.639764),
                LatLng(45.457361, -73.639764),
                LatLng(45.457333, -73.639769),
                LatLng(45.457280, -73.639800),
                LatLng(45.457231, -73.639884),
                LatLng(45.457226, -73.639919),
                LatLng(45.457213, -73.639990),
                LatLng(45.457218, -73.640016)
            ).fillColor(concordiaRed)
        val buildingGE = PolygonOptions()
            .add(
                LatLng(45.457040, -73.640163),
                LatLng(45.456800, -73.640347),
                LatLng(45.456896, -73.640609),
                LatLng(45.456870, -73.640628),
                LatLng(45.456893, -73.640691),
                LatLng(45.456918, -73.640672),
                LatLng(45.456944, -73.640742),
                LatLng(45.457176, -73.640570),
                LatLng(45.457132, -73.640451),
                LatLng(45.457139, -73.640436),
                LatLng(45.457040, -73.640163)
            ).fillColor(concordiaRed)
        val buildingCC = PolygonOptions()
            .add(
                LatLng(45.458078, -73.640019),
                LatLng(45.458175, -73.640259),
                LatLng(45.458168, -73.640262),
                LatLng(45.458280, -73.640567),
                LatLng(45.458290, -73.640561),
                LatLng(45.458388, -73.640800),
                LatLng(45.458527, -73.640687),
                LatLng(45.458440, -73.640453),
                LatLng(45.458447, -73.640446),
                LatLng(45.458329, -73.640142),
                LatLng(45.458324, -73.640148),
                LatLng(45.458228, -73.639905),
                LatLng(45.458078, -73.640019)
            ).fillColor(concordiaRed)
        val buildingSP = PolygonOptions()
            .add(
                LatLng(45.456985, -73.640828),
                LatLng(45.457027, -73.640936),
                LatLng(45.456998, -73.640959),
                LatLng(45.457017, -73.641013),
                LatLng(45.457042, -73.640993),
                LatLng(45.457160, -73.641295),
                LatLng(45.457150, -73.641303),
                LatLng(45.457179, -73.641385),
                LatLng(45.457170, -73.641394),
                LatLng(45.457185, -73.641433),
                LatLng(45.457210, -73.641414),
                LatLng(45.457439, -73.642004),
                LatLng(45.457640, -73.641848),
                LatLng(45.457673, -73.641925),
                LatLng(45.458327, -73.641413),
                LatLng(45.458277, -73.641284),
                LatLng(45.458210, -73.641338),
                LatLng(45.458180, -73.641263),
                LatLng(45.458255, -73.641202),
                LatLng(45.458194, -73.641039),
                LatLng(45.458339, -73.640921),
                LatLng(45.458316, -73.640863),
                LatLng(45.458000, -73.641112),
                LatLng(45.457977, -73.641067),
                LatLng(45.457895, -73.641131),
                LatLng(45.457907, -73.641170),
                LatLng(45.457526, -73.641470),
                LatLng(45.457204, -73.640659),
                LatLng(45.456985, -73.640828)
            ).fillColor(concordiaRed)
        val buildingRF = PolygonOptions()
            .add(
                LatLng(45.458439, -73.640752),
                LatLng(45.458465, -73.640813),
                LatLng(45.458418, -73.640855),
                LatLng(45.458472, -73.641009),
                LatLng(45.458383, -73.641079),
                LatLng(45.458434, -73.641204),
                LatLng(45.458514, -73.641143),
                LatLng(45.458546, -73.641230),
                LatLng(45.458528, -73.641247),
                LatLng(45.458540, -73.641277),
                LatLng(45.458489, -73.641317),
                LatLng(45.458512, -73.641377),
                LatLng(45.458642, -73.641270),
                LatLng(45.458648, -73.641283),
                LatLng(45.458823, -73.641152),
                LatLng(45.458783, -73.641031),
                LatLng(45.458767, -73.641050),
                LatLng(45.458684, -73.640808),
                LatLng(45.458592, -73.640880),
                LatLng(45.458544, -73.640753),
                LatLng(45.458519, -73.640774),
                LatLng(45.458492, -73.640710),
                LatLng(45.458439, -73.640752)
            ).fillColor(concordiaRed)
        val buildingAD = PolygonOptions()
            .add(
                LatLng(45.458003, -73.639682),
                LatLng(45.458026, -73.639748),
                LatLng(45.457924, -73.639833),
                LatLng(45.457910, -73.639808),
                LatLng(45.457901, -73.639815),
                LatLng(45.457880, -73.639764),
                LatLng(45.457798, -73.639829),
                LatLng(45.457911, -73.640131),
                LatLng(45.457993, -73.640070),
                LatLng(45.457967, -73.640013),
                LatLng(45.458274, -73.639756),
                LatLng(45.458306, -73.639827),
                LatLng(45.458385, -73.639761),
                LatLng(45.458260, -73.639441),//
                LatLng(45.458175, -73.639510),
                LatLng(45.458201, -73.639575),
                LatLng(45.458192, -73.639581),
                LatLng(45.458203, -73.639617),//
                LatLng(45.458088, -73.639694),
                LatLng(45.458070, -73.639641),
                LatLng(45.458003, -73.639682)
            ).fillColor(concordiaRed)
        val buildingFC = PolygonOptions()
            .add(
                LatLng(45.458424, -73.639163),
                LatLng(45.458431, -73.639178),
                LatLng(45.458423, -73.639194),
                LatLng(45.458444, -73.639256),
                LatLng(45.458460, -73.639246),
                LatLng(45.458516, -73.639405),
                LatLng(45.458512, -73.639412),
                LatLng(45.458516, -73.639422),
                LatLng(45.458488, -73.639444),
                LatLng(45.458541, -73.639587),
                LatLng(45.458560, -73.639596),
                LatLng(45.458584, -73.639577),
                LatLng(45.458591, -73.639596),
                LatLng(45.458600, -73.639593),
                LatLng(45.458629, -73.639666),
                LatLng(45.458671, -73.639685),
                LatLng(45.458748, -73.639623),
                LatLng(45.458766, -73.639543),
                LatLng(45.458718, -73.639422),
                LatLng(45.458727, -73.639419),
                LatLng(45.458710, -73.639365),
                LatLng(45.458740, -73.639334),
                LatLng(45.458729, -73.639296),
                LatLng(45.458709, -73.639315),
                LatLng(45.458702, -73.639302),
                LatLng(45.458690, -73.639313),
                LatLng(45.458674, -73.639287),
                LatLng(45.458670, -73.639294),
                LatLng(45.458613, -73.639134),
                LatLng(45.458620, -73.639129),
                LatLng(45.458627, -73.639101),
                LatLng(45.458616, -73.639067),
                LatLng(45.458592, -73.639056),
                LatLng(45.458581, -73.639061),
                LatLng(45.458577, -73.639050),
                LatLng(45.458424, -73.639163)
            ).fillColor(concordiaRed)
        val buildingVL = PolygonOptions()
            .add(
                LatLng(45.458634, -73.638466),
                LatLng(45.458843, -73.639029),
                LatLng(45.459048, -73.638869),
                LatLng(45.459096, -73.638983),
                LatLng(45.459080, -73.638994),
                LatLng(45.459093, -73.639037),
                LatLng(45.458990, -73.639120),
                LatLng(45.459111, -73.639419),
                LatLng(45.459217, -73.639336),
                LatLng(45.459239, -73.639385),
                LatLng(45.459310, -73.639321),
                LatLng(45.459272, -73.639225),
                LatLng(45.459321, -73.639184),
                LatLng(45.459337, -73.639223),
                LatLng(45.459357, -73.639210),
                LatLng(45.459370, -73.639238),
                LatLng(45.459492, -73.639137),
                LatLng(45.459311, -73.638674),
                LatLng(45.459318, -73.638661),
                LatLng(45.459139, -73.638198),
                LatLng(45.459217, -73.638135),
                LatLng(45.459144, -73.637939),
                LatLng(45.459138, -73.637942),
                LatLng(45.459079, -73.637864),
                LatLng(45.459083, -73.637889),
                LatLng(45.459055, -73.637912),
                LatLng(45.459045, -73.637895),
                LatLng(45.458903, -73.638000),
                LatLng(45.458914, -73.638025),
                LatLng(45.458884, -73.638046),
                LatLng(45.458903, -73.638098),
                LatLng(45.458875, -73.638122),
                LatLng(45.458891, -73.638171),
                LatLng(45.458854, -73.638200),
                LatLng(45.458855, -73.638199),
                LatLng(45.458869, -73.638239),
                LatLng(45.458707, -73.638360),
                LatLng(45.458719, -73.638399),
                LatLng(45.458634, -73.638466)
            ).fillColor(concordiaRed)
        val buildingPY= PolygonOptions()
            .add(
                LatLng(45.458731, -73.640448),
                LatLng(45.458822, -73.640695),
                LatLng(45.458800, -73.640713),
                LatLng(45.458850, -73.640835),
                LatLng(45.459180, -73.640578),
                LatLng(45.459215, -73.640634),
                LatLng(45.459290, -73.640560),
                LatLng(45.459124, -73.640130),
                LatLng(45.459025, -73.640206),
                LatLng(45.459033, -73.640221),
                LatLng(45.459002, -73.640246),
                LatLng(45.458991, -73.640213),
                LatLng(45.458758, -73.640398),
                LatLng(45.458765, -73.640418),
                LatLng(45.458731, -73.640448)
            ).fillColor(concordiaRed)

        mMap.addPolygon(buildingCJ)
        mMap.addPolygon(buildingGE)
        mMap.addPolygon(buildingCC)
        mMap.addPolygon(buildingSP)
        mMap.addPolygon(buildingRF)
        mMap.addPolygon(buildingAD)
        mMap.addPolygon(buildingFC)
        mMap.addPolygon(buildingVL)
        mMap.addPolygon(buildingPY)

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
            ).fillColor(concordiaRed)
        val buildingGM = PolygonOptions()
            .add(
                LatLng(45.495651, -73.578809),
                LatLng(45.495780, -73.579088),
                LatLng(45.495761, -73.579105),
                LatLng(45.495783, -73.579151),
                LatLng(45.496133, -73.578808),
                LatLng(45.495977, -73.578482)
            ).fillColor(concordiaRed)
        val buildingH  = PolygonOptions()
            .add(
                LatLng(45.496832, -73.578850),
                LatLng(45.497173, -73.579553),
                LatLng(45.497729, -73.579034),
                LatLng(45.497380, -73.5783300),
                LatLng(45.496832, -73.578850)
            ).fillColor(concordiaRed)
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
            ).fillColor(concordiaRed)
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
            ).fillColor(concordiaRed)
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
            ).fillColor(concordiaRed)
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
            ).fillColor(concordiaRed)

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
            ).fillColor(concordiaRed)
        mMap.addPolygon(buildingEV)
        mMap.addPolygon(buildingGM)
        mMap.addPolygon(buildingH)
        mMap.addPolygon(buildingFG)
        mMap.addPolygon(buildingMB)
        mMap.addPolygon(buildingLB)
        mMap.addPolygon(buildingGN)
        mMap.addPolygon(buildingFB)
    }


    // For sample unit tests, remove for sprint 2.
    fun sum(a: Int, b: Int) = a + b

    fun square(num: Double) = num * num

    fun reverse(word: String) = word.reversed()

    fun canYouDrink(age: Int): Boolean {
        return age >= 18
    }
}
