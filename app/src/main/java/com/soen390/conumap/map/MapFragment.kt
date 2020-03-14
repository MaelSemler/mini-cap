package com.soen390.conumap.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.soen390.conumap.R
import com.soen390.conumap.permission.Permission

class MapFragment : Fragment() {


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        //Call the Map setup method to set all the default stuff for the map
        Map.setUpMap(googleMap, activity!!)

        //Added a marker to test the method
        //Map.addMarker(LatLng(45.495418, -73.579169), "Bonjour")


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.map_fragment, container, false)
        val userLocationButton = root.findViewById<View>(R.id.user_location_button)
        val loyolaButton = root.findViewById<View>(R.id.loy_button)
        val sgwButton = root.findViewById<View>(R.id.sgw_button)

        userLocationButton.setOnClickListener{
            Map.centerMapOnUserLocation(activity!!)
        }
        loyolaButton.setOnClickListener{
            Map.focusOnCampus("Loyola")
        }
        sgwButton.setOnClickListener{
            Map.focusOnCampus("SGW")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}