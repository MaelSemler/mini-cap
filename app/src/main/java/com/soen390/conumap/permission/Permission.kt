package com.soen390.conumap.permission

import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.fragment.app.FragmentActivity
import java.util.*

object Permission {
    fun checkPermission(activity: FragmentActivity): Boolean {
        return ActivityCompat.checkSelfPermission(activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: FragmentActivity, code: Int) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), code)
    }
}