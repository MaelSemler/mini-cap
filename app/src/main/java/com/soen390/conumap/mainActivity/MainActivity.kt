package com.soen390.conumap.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button

import com.google.android.material.navigation.NavigationView

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.soen390.conumap.IndoorNavigation.IndoorActivity
import com.soen390.conumap.IndoorNavigation.IndoorDatabaseHelper
import com.soen390.conumap.R


import com.soen390.conumap.helper.ContextPasser
import com.soen390.conumap.map.Map
import com.soen390.conumap.permission.Permission

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var db: IndoorDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.Directions,
            R.id.nav_slideshow,
            R.id.mapFragment
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Pass context to other files that require it.
        ContextPasser.setContexts(this)

        // Check if the database is populated and if it isn't, fill it up.
        db = IndoorDatabaseHelper(this)
        if(db.getNumberOfRows() == 0) {
            db.addAllInfoToTable(resources.getStringArray(R.array.floor_nodes))
        }
    }

    override fun onResume() {
        super.onResume()

        // Makes sure locating circle is visible map moves to user location.
        // This is primarily used when user exits app and toggles the app's location permission.
        if(Map.mapLateinitsAreInitialized() && Permission.checkLocationPermission(this)) {
            Map.getMapInstance().isMyLocationEnabled = true // Makes sure blue "I am here" dot shows.
            Map.centerMapOnUserLocation(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Permission.handlePermissionResults(requestCode, permissions, grantResults, this)
    }
}
