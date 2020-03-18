package com.soen390.conumap

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.soen390.conumap.map.Map
import com.nhaarman.mockitokotlin2.*
import com.soen390.conumap.permission.Permission
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class Permissions {

    @Test
    fun checkPermission() {
        assertEquals(Permission.checkPermission(FragmentActivity()), true)
    }

    @Test
    fun requestPermission() {
        Permission.requestPermission(FragmentActivity(), 1)
    }
}
