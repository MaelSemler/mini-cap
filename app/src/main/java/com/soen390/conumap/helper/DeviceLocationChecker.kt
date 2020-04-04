package com.soen390.conumap.helper

import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.provider.Settings

// An object used to check if the location service on the device is enabled.
object DeviceLocationChecker {
    private lateinit var context: Context
    // For API level >= 28 (Pie or newer)
    private lateinit var locationManager: LocationManager
    // For API level < 28 (Older than Pie)
    private var locationMode = -1 // 0 means off, != 0  means on

    fun setUp(ctx: Context) {
        context = ctx
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
    }

    // This will be called before checking locationMode.
    // For the case where the user opens the app and toggles device location while app is open.
    private fun updateLocationMode() {
        locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
    }

    // Returns true if device location is enabled, false otherwise.
    fun isDeviceLocationEnabled(): Boolean {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            // Use locationMode since old version of Android.
            updateLocationMode()
            return locationMode != 0
        } else {
            // Use locationManager for newer versions.
            return locationManager.isLocationEnabled
        }
    }
}