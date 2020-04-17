package com.soen390.conumap.IndoorNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.SVGConverter.ConverterToFloorPlan
import com.soen390.conumap.helper.ContextPasser
import kotlinx.android.synthetic.main.activity_indoor.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IndoorActivity : AppCompatActivity() {
    lateinit var db: IndoorDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.indoor_nav)

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()

        val floorConverter = ConverterToFloorPlan

        var tempBitmap = floorConverter.svgToBitMap()

        GlobalScope.launch {
            // Floorplan
            val floorP =  floorConverter.convertToPlan(tempBitmap)
        }

        // Demo so people can see how to use the database.
        db = IndoorDatabaseHelper(this)

        db.emptyDatabaseContents()

        db.insertData("H", "9", "937", "H-937", "10", "12")
        db.insertData("H", "8", "801", "H-801", "5", "9")
        db.insertData("H", "8", "WF", "Water Fountain", "6", "13")
        db.insertData("H", "9", "VM", "Vending Machine", "2", "8")

        db.printDatabaseContents()

        var a = db.getRoomCoordinates("H-937")
        var b = db.getRoomCoordinates("H-801")
        var c = db.getPOICoordinates("Water Fountain", 8)
        var d = db.getPOICoordinates("Vending Machine", 9)
        var e = db.getPOICoordinates("Nonsense", 6) // Should be error.

        println(a.toString() + "\n" + b.toString() + "\n" + c.toString() + "\n" + d.toString() + "\n" + e.toString())
    }

}
