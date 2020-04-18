package com.soen390.conumap.IndoorNavigation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.indoor_nav)

        ContextPasser.setContextIndoor(this)

        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())

        val floorConverter = ConverterToFloorPlan

        var tempBitmap = floorConverter.svgToBitMap()

        GlobalScope.launch {
            // Floorplan
            val floorP =  floorConverter.convertToPlan(tempBitmap)

            var blockRow: ArrayList<Int> = arrayListOf()
            var blockCol: ArrayList<Int> = arrayListOf()

            for (array in floorP.floorNodes) {
                for (value in array) {
                    if (value.walkable == true) {
                    } else {
                        blockCol.add(value.yInd)
                        blockRow.add(value.xInd)
                    }
                }
            }

            var blockArray = arrayOf(blockRow, blockCol)

            var pathfinding: Pathfinding = Pathfinding(floorP.floorNodes.size,floorP.floorNodes[0].size, Node(31,291), Node(354,307))

            pathfinding.loadMap()
            pathfinding.loadBlocks(blockArray)
            var path: MutableList<Node> = pathfinding.findPath()

            /* Uncomment to print all walkable nodes to console. */
//            for (array in floorP.floorNodes) {
//                for (value in array) {
//                    if (value.walkable == true) {
//                        println("Node("+value.xInd+","+value.yInd+"),")
//                    }
//                }
//            }

            for (node: Node in path) {
                println(""+node+",")
            }
        }
    }

    fun showIndoorPath(resource: Int, indoorPath: Array<Node>) {
        imageRecycler.adapter = ImageAdapter(resource, indoorPath)
    }

    fun h9Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h9floorplan, arrayOf())
    }

    fun h8Button(view: View){
        val h9button = findViewById<View>(R.id.hfloor_nine_button)
        h9button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        val h8button = findViewById<View>(R.id.hfloor_eight_button)
        h8button.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        imageRecycler.adapter = ImageAdapter(R.drawable.h8floorplan, arrayOf())
    }
}
