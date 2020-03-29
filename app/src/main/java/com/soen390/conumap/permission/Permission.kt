package com.soen390.conumap.permission

import android.content.Context
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.soen390.conumap.map.Map

object Permission {
    private lateinit var context: Context

    fun setContext(ctx: Context) { context = ctx }

    fun checkLocationPermission(activity: FragmentActivity): Boolean {
        return ActivityCompat.checkSelfPermission(activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(activity: FragmentActivity, code: Int) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), code)
    }

    fun handlePermissionResults(requestCode: Int, permissions: Array<String>, grantResults: IntArray, activity: FragmentActivity) {
        when(requestCode) {
            Map.LOCATION_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User accepted the location permission.
                    Map.getMapInstance().isMyLocationEnabled = true
                    Map.centerMapOnUserLocation(activity)
                } else {
                    // User rejected the location permission.
                    val locationDeniedMessage: Toast = Toast.makeText(
                        context,
                        "Please allow location access to use all app features.",
                        Toast.LENGTH_LONG
                    )
                    // Position the toast in the center so it is more likely to be seen by user.
                    locationDeniedMessage.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                    locationDeniedMessage.show()
                }
                return
            }
        }
    }
}