package com.soen390.conumap.pathDemo

import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.Pathfinding
import com.soen390.conumap.building.FloorCreator
import com.soen390.conumap.building.Floor


fun main (args: Array<String>) {
    var origin = Node (1,1)
    var destination = Node (9, 17)
    var floorMap: Floor.FloorPlan
    var blockRow: ArrayList<Int> = arrayListOf()
    var blockCol: ArrayList<Int> = arrayListOf()

    FloorCreator.createFloors()

    //Temporarily hardcoded to first index since that's where H9 floor plan is currently stored
    floorMap = FloorCreator.floors[0].floorPlan

    var floorNodes = floorMap.floorNodes

    for (array in floorNodes.reversedArray()) {
        for (value in array) {
            if (value.walkable == true) {
                print ("-    ")
            } else {
                print("O    ")
                blockRow.add(value.yInd)
                blockCol.add(value.xInd)
            }
        }
        println()
    }

    var blockArray = arrayOf(blockRow, blockCol)

    var pathfinding: Pathfinding = Pathfinding(floorMap.floorNodes.size,floorMap.floorNodes[0].size, origin, destination)

    pathfinding.printMapSizeToConsole()

    pathfinding.loadMap()
    pathfinding.loadBlocks(blockArray)
    pathfinding.printMapToConsole()
    var path: MutableList<Node> = pathfinding.findPath()
    for (node: Node in path) {
        println(node)
    }
}