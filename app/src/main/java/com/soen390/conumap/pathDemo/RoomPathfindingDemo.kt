package com.soen390.conumap.pathDemo

import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.Pathfinding
import com.soen390.conumap.building.FloorCreator
import com.soen390.conumap.building.Floor


fun main (args: Array<String>) {
    var origin = Node (0,0)
    var destination = Node (10, 10)
    var floorMap: Floor.FloorPlan

    FloorCreator.createFloors()

    floorMap = FloorCreator.floors[0].floorPlan

    var pathfinding: Pathfinding = Pathfinding(17, 19, origin, destination)

    pathfinding.loadMap()
    pathfinding.printMapSizeToConsole()
    pathfinding.printMapToConsole()

}