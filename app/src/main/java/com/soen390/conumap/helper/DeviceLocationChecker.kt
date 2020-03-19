package com.soen390.conumap.helper

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

// An object used to check if the location service on the device is enabled.
object DeviceLocationChecker {
    private lateinit var context: Context
    fun setContext(ctx: Context) { context = ctx
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    lateinit var locationManager: LocationManager

    @RequiresApi(Build.VERSION_CODES.P)
    fun isDeviceLocationEnabled(): Boolean {
        return locationManager.isLocationEnabled()
    }
}