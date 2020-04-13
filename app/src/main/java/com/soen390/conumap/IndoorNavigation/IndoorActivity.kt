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

        GlobalScope.launch {

            //@Andy Here is your floorplan
            val floorP =  floorConverter.convertToPlan(tempBitmap)

//          Proof that this is workinggg
            Log.i("TESTING: ",floorP.floorNodes[430][330].color)

            var blockRow: ArrayList<Int> = arrayListOf()
            var blockCol: ArrayList<Int> = arrayListOf()

            for (array in floorP.floorNodes.reversedArray()) {
                for (value in array) {
                    if (value.walkable == true) {
                    } else {
                        blockRow.add(value.yInd)
                        blockCol.add(value.xInd)
                    }
                }
            }

            var blockArray = arrayOf(blockRow, blockCol)

            var pathfinding: Pathfinding = Pathfinding(floorP.floorNodes.size,floorP.floorNodes[0].size, Node(384,38), Node(731,208))

            pathfinding.loadMap()
            pathfinding.loadBlocks(blockArray)
            var path: MutableList<Node> = pathfinding.findPath()

            for (node: Node in path) {
                println(node)
            }

            Log.i("Algorithm","Done running algorithm...")
        }

    }
}
