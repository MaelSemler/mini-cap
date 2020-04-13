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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()

        val floorConverter = ConverterToFloorPlan

        var tempBitmap = floorConverter.svgToBitMap()
        print("HELLO")

        GlobalScope.launch {


            val floorP =  floorConverter.convertToPlan(tempBitmap)

            print(floorP.floorNodes[430][330].color)
        }

    }
}
