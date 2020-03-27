package com.soen390.conumap.IndoorNavigation

import java.nio.file.Path

fun main (args: Array<String>) {
    var origin: Node = Node(2, 1)
    var destination: Node = Node(7, 7)
    var row = 8
    var col = 8
    var pathfindingTest: Pathfinding = Pathfinding(row, col, origin, destination)
    var blockRow = arrayOf(1,2,3,4,5,6,7,7,6,5,3,3,3)
    var blockCol = arrayOf(3,3,3,3,3,3,3,6,6,6,5,6,7)
    var blockArray = arrayOf<Array<Int>>(blockRow, blockCol)
    pathfindingTest.loadMap()
    pathfindingTest.loadBlocks(blockArray)
    pathfindingTest.printMapSizeToConsole()
    pathfindingTest.printMapToConsole()

    var path: MutableList<Node> = pathfindingTest.findPath()
    for (node:Node in path) {
        println(node)
    }

}
