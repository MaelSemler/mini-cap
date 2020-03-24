package com.soen390.conumap.IndoorNavigation

/*
 * The indoor navigation algorithm for ConUMaps.
 * The algorithm is based on the A* path-finding algorithm in order to determine the quickest path
 * from one room to another, or from one room to an indoor point of interest.
 *
 * NOTE: It currently only works with vertical/horizontal movements
 */

class Pathfinding (rows: Int, cols: Int, origin: Node, destination: Node) {
    val moveCost: Int = 1
    var origin: Node = origin
    var destination: Node = destination
    var mapArray: Array<Array<Node>> = Array(rows) {Array(cols) {Node(0,0)}}
    var openSet = mutableListOf<Node>()
    var closedSet = mutableListOf<Node>()

    fun findPath(): MutableList<Node> {
        openSet.add(origin)
        while (openSet.size > 0) {
            var currentNode: Node = openSet.removeAt(0)
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

    fun getPath(currentNode: Node): MutableList<Node> {
        var path: MutableList<Node> = arrayListOf()
        var currentNode = currentNode
        var parentNode: Node = currentNode
        path.add(currentNode)
        while ((parentNode.equals(currentNode.parent)) != null) {
            path.add(0, parentNode)
            currentNode = parentNode
        }
        return path
    }

    //Sets every tile in the map as nodes
    fun loadMap(rows:Int, cols: Int) {
        var currentNode: Node
        for (i in 0..rows-1) {
            var tempArray = arrayOf<Node>()
            for (j in 0..cols-1) {
                currentNode = Node(i, j)
                currentNode.calculateH(destination)
                tempArray += currentNode
            }
            mapArray += tempArray
        }
    }

    //Sets the map with blocks i.e. tiles that cannot be walked on
    //Parameter is array of nodes that are blocks
    fun loadBlocks(blockArray: Array<Array<Int>>) {
        for (i in 0..blockArray.size) {
            var row: Int = blockArray [i][0]
            var col: Int = blockArray [i][1]
            mapArray[row][col].makeBlock(true)
        }
    }

    fun addNeighbours(currentNode: Node) {
        var upperRow = currentNode.row + 1
        var middleRow = currentNode.row
        var lowerRow = currentNode.row - 1
        var col = currentNode.col
        if (upperRow > mapArray.size) {
            checkNode(currentNode, upperRow, col, moveCost)
        }
        if (col - 1 >= 0 ) {
            checkNode(currentNode, middleRow, col - 1, moveCost)
        }
        if (col +1 <= 0) {
            checkNode(currentNode, middleRow, col +1 , moveCost)
        }
        if (lowerRow < mapArray.size) {
            checkNode(currentNode, lowerRow, col, moveCost)
        }
    }

    fun checkNode(currentNode: Node, row: Int, col: Int, cost: Int) {
        var neighbourNode: Node = mapArray[row][col]
        if (!mapArray[row][col].isBlock && !closedSet.contains(neighbourNode)) {
            if (!openSet.contains(neighbourNode)) {
                neighbourNode.updateNode(currentNode, cost)
                openSet.add(neighbourNode)
            }
            else {
                var isUpdated: Boolean = neighbourNode.checkAlternative(currentNode,cost)
                if (isUpdated) {
                    //TODO: check if this is working properly
                    openSet.remove(neighbourNode)
                    openSet.add(neighbourNode)
                }
            }
        }
    }

}