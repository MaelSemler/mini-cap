package com.soen390.conumap.IndoorNavigation

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.PriorityQueue

/*
 * The indoor navigation algorithm for ConUMaps.
 * The algorithm is based on the A* path-finding algorithm in order to determine the quickest path
 * from one room to another, or from one room to an indoor point of interest.
 *
 * NOTE: Distances are calculated using Manhattan distance.
 * NOTE2: Graph coordinates start from top left corner (0,0) and increase rightwards and downwards.
 */

class Pathfinding (rows: Int, cols: Int, origin: Node, destination: Node) {
    val moveCost: Int = 1
    var origin: Node = origin
    var destination: Node = destination
    var mapArray: Array<Array<Node>> = Array(cols) {Array(rows) {Node(0,0)}}
    @RequiresApi(Build.VERSION_CODES.N)
    var openSet = PriorityQueue(NodeComparator)
    var closedSet = mutableListOf<Node>()

    fun findPath(): MutableList<Node> {
        openSet.add(origin)
        while (openSet.size > 0) {
            var currentNode: Node = openSet.poll()
            closedSet.add(currentNode)
            if (currentNode.equals(destination)) {
                return getPath(currentNode)
            } else {
                addNeighbours(currentNode)
            }
        }
        var noPath:MutableList<Node> = mutableListOf()
        return  noPath
    }

    //Retrieve the path from origin to destination
    fun getPath(currentNode: Node): MutableList<Node> {
        var path: MutableList<Node> = arrayListOf()
        var currentNode = currentNode
        var parentNode: Node? = currentNode.parent
        path.add(currentNode)
        while (parentNode != null) {
            path.add(0, parentNode)
            currentNode = parentNode
            parentNode = currentNode.parent
        }
        return path
    }

    //Sets every tile in the map as nodes
    fun loadMap() {
        var currentNode: Node
        for (i in 0..mapArray.size-1) {
            var tempArray = arrayOf<Node>()
            for (j in 0..mapArray[0].size-1) {
                currentNode = Node(i, j)
                currentNode.calculateH(destination)
                tempArray += currentNode
                mapArray[i][j] = (currentNode)
            }
        }
    }

    //Sets the map with blocks i.e. tiles that cannot be walked on
    //Parameter is array of nodes that are blocks
    fun loadBlocks(blockArray: Array<ArrayList<Int>>) {
        var rowArray = arrayListOf<Int>()
        var colArray = arrayListOf<Int>()
        var counter = 0
        for (tempArray in blockArray) {
            if (counter == 0)
                colArray = tempArray
            else {
                rowArray = tempArray
            }
            counter ++
        }
        for (i in 0..colArray.size-1) {
            mapArray[colArray[i]][rowArray[i]].makeBlock(true)
        }
    }

    //Add neighbours of current node to the open set using checkNode() function
    fun addNeighbours(currentNode: Node) {
        var upperRow = currentNode.row - 1
        var middleRow = currentNode.row
        var lowerRow = currentNode.row + 1
        var col = currentNode.col
        if (upperRow >= 0) {
            checkNode(currentNode, upperRow, col, moveCost)
        }
        if (col - 1 >= 0 ) {
            checkNode(currentNode, middleRow, col - 1, moveCost)
        }
        if (col + 1 < mapArray[0].size) {
            checkNode(currentNode, middleRow, col +1 , moveCost)
        }
        if (lowerRow < mapArray.size) {
            checkNode(currentNode, lowerRow, col, moveCost)
        }
    }

    //Checks to see if a given node is valid (i.e. not a block) and adds it to the open set
    fun checkNode(currentNode: Node, row: Int, col: Int, cost: Int) {
        var neighbourNode: Node = mapArray[col][row]
        if (!mapArray[col][row].isBlock && !closedSet.contains(neighbourNode)) {
            if (!openSet.contains(neighbourNode)) {
                neighbourNode.updateNode(currentNode, cost)
                openSet.add(neighbourNode)
            }
            else {
                var isUpdated: Boolean = neighbourNode.checkAlternative(currentNode,cost)
                if (isUpdated) {
                    //Refresh the priority queue
                    openSet.remove(neighbourNode)
                    openSet.add(neighbourNode)
                }
            }
        }
    }

    fun printMapSizeToConsole() {
        println("Map size: "+mapArray.size+" columns x "+mapArray[0].size+" rows")
    }

    fun printMapToConsole() {
        println("Legend: ")
        (println("-: Walkable node"))
        println("X: Block node")
        println("O: Origin")
        println("D: Destination")

        for (y in 0..mapArray[0].size-1) {
            for (x in 0..mapArray.size-1) {
                if (mapArray[x][y].equals(origin)){
                    print("O    ")
                } else if (mapArray[x][y].equals(destination)) {
                    print("D    ")
                } else if (mapArray[x][y].isBlock) {
                    print("X    ")
                } else {
                    print("-    ")
                }
            }
            println()
        }
    }

}