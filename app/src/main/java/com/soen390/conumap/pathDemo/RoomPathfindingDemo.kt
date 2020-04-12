package com.soen390.conumap.pathDemo

import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.Pathfinding
import com.soen390.conumap.building.FloorCreator
import com.soen390.conumap.building.Floor


fun main (args: Array<String>) {
    var origin = Node (0,0)
    var destination = Node (0, 1)
    var floorMap: Floor.FloorPlan

    FloorCreator.createFloors()

    //Temporarily hardcoded to first index since that's where H9 floor plan is currently stored
    floorMap = FloorCreator.floors[0].floorPlan

    for (array in floorMap.floorNodes) {
        for (value in array) {
            if (value.walkable == true) {
                print ("-   ")
            } else if (value.walkable == false) {
                print("X   ")
            }
        }
        println()
    }

    var pathfinding: Pathfinding = Pathfinding(floorMap.floorNodes.size,floorMap.floorNodes[0].size, origin, destination)

    pathfinding.loadMap()
    pathfinding.printMapSizeToConsole()
    //pathfinding.printMapToConsole()

}