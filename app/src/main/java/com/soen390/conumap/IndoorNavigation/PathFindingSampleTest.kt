package com.soen390.conumap.IndoorNavigation

import java.nio.file.Path

fun main (args: Array<String>) {
    var origin: Node = Node(2, 1)
    var destination: Node = Node(2, 5)
    var row = 6
    var col = 7
    var pathfindingTest: Pathfinding = Pathfinding(row, col, origin, destination)
    var blockRow = arrayOf(1,2,3)
    var blockCol = arrayOf(3,3,3)
    var blockArray = arrayOf<Array<Int>>(blockRow, blockCol)
    println(blockArray[0][0])
    pathfindingTest.loadBlocks(blockArray)
//    var path: MutableList<Node> = pathfindingTest.findPath()
//    for (node:Node in path) {
//        println(node)
//    }

}
