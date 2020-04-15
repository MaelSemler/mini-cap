package com.soen390.conumap.IndoorNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()

        val floorConverter = ConverterToFloorPlan

        var tempBitmap = floorConverter.svgToBitMap()

        GlobalScope.launch {

            //@Andy Here is your floorplan
            val floorP =  floorConverter.convertToPlan(tempBitmap)

//            Proof that this is workinggg
            Log.i("TESTING: ",floorP.floorNodes[430][330].color)
        }

        db = IndoorDatabaseHelper(this)

        db.emptyDatabaseContents()

        db.printDatabaseContents()

        db.insertData("", "", "", "H-937", "10", "12")
        db.insertData("", "", "", "H-801", "5", "9")

        db.printDatabaseContents()

        var one = db.getRoomCoordinates("H-937")
        var two = db.getRoomCoordinates("H-801")
        var thr = db.getRoomCoordinates("Nonsense value to test error")

        println("DBTest:")
        println("one: " + one[0] + ", " + one[1])
        println("two: " + two[0] + ", " + two[1])
        println("thr: " + thr[0] + ", " + thr[1])
    }
}
