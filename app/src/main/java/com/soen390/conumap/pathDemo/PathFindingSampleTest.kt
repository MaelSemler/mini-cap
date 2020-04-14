package com.soen390.conumap.pathDemo

import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.Pathfinding

fun main (args: Array<String>) {
    var origin: Node =
        Node(1, 2)
    var destination: Node =
        Node(7, 7)
    var row = 8
    var col = 8
    var pathfindingTest: Pathfinding =
        Pathfinding(
            row,
            col,
            origin,
            destination
        )
    var blockRow = arrayListOf<Int>(1,2,3,4,5,6,7,7,6,5,3,3,3)
    var blockCol = arrayListOf<Int>(3,3,3,3,3,3,3,6,6,6,5,6,7)
    var blockArray = arrayOf<ArrayList<Int>>(blockCol, blockRow)
    pathfindingTest.loadMap()
    pathfindingTest.loadBlocks(blockArray)
    pathfindingTest.printMapSizeToConsole()
    pathfindingTest.printMapToConsole()

    var path: MutableList<Node> = pathfindingTest.findPath()
    for (node: Node in path) {
        println(node)
    }

}
