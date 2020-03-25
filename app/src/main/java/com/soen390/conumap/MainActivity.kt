package com.soen390.conumap

import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast

import com.google.android.material.navigation.NavigationView

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
//import com.soen390.conumap.Directions.directions
//import com.soen390.conumap.databinding.DirectionsFragmentBinding
import com.soen390.conumap.ui.directions.DirectionsViewModel
import com.soen390.conumap.building.BuildingCreator.setContext

import com.soen390.conumap.helper.ContextPasser
import com.soen390.conumap.map.Map
import com.soen390.conumap.permission.Permission

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

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
                R.id.nav_home, R.id.Directions, R.id.nav_slideshow,R.id.mapFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Pass context to other files that require it.
        ContextPasser.setContexts(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
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
