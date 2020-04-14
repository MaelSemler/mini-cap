package com.soen390.conumap.pathDemo

import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.Pathfinding
import com.soen390.conumap.building.FloorCreator
import com.soen390.conumap.building.Floor

fun main (args: Array<String>) {
    var origin = Node (1,1)
    var destination = Node (1, 2)
    var floorMap: Floor.FloorPlan
    var blockRow: ArrayList<Int> = arrayListOf()
    var blockCol: ArrayList<Int> = arrayListOf()

    FloorCreator.createFloors()

    //Hardcoded to first index since that's where H9 floor plan is currently stored
    floorMap = FloorCreator.floors[0].floorPlan

    var floorNodes = floorMap.floorNodes

    for (array in floorNodes) {
        for (value in array) {
            if (!value.walkable) {
                blockRow.add(value.yInd)
                blockCol.add(value.xInd)
            }
        }
    }

    var blockArray = arrayOf(blockCol, blockRow)

    var pathfinding = Pathfinding(floorMap.floorNodes.size,floorMap.floorNodes[0].size, origin, destination)

    pathfinding.printMapSizeToConsole()

    pathfinding.loadMap()
    pathfinding.loadBlocks(blockArray)
    pathfinding.printMapToConsole()
    var path: MutableList<Node> = pathfinding.findPath()
    for (node: Node in path) {
        println(node)
    }
}