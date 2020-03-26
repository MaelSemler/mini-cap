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
    var openSet = mutableListOf<Node>() //TODO: this should be changed to priority queue
    var closedSet = mutableListOf<Node>()

    fun findPath(): MutableList<Node> {
        openSet.add(origin)
        var i = 0
        while (openSet.size > 0) {
            var currentNode: Node = openSet.removeAt(0)
            closedSet.add(currentNode)
            if (currentNode.equals(destination)) {
                return getPath(currentNode)
            } else {
                addNeighbours(currentNode)
            }
            println(i)
            println(currentNode)
            i++
        }
        var noPath:MutableList<Node> = mutableListOf()
        println("There is no path")
        return  noPath
    }

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
                mapArray[i][j]=(currentNode)
            }
        }
    }

    //Sets the map with blocks i.e. tiles that cannot be walked on
    //Parameter is array of nodes that are blocks
    fun loadBlocks(blockArray: Array<Array<Int>>) {
        var rowArray = arrayOf<Int>()
        var colArray = arrayOf<Int>()
        var counter = 0
        for (tempArray in blockArray) {
            if (counter == 0)
                rowArray = tempArray
            else {
                colArray = tempArray
            }
            counter ++
        }
        for (i in 0..rowArray.size-1) {
            mapArray[rowArray[i]][colArray[i]].makeBlock(true)
        }
    }

    //Add neighbours of current node to the open set using checkNode() function
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
        if (col + 1 < mapArray[0].size) {
            checkNode(currentNode, middleRow, col +1 , moveCost)
        }
        if (lowerRow < mapArray.size && lowerRow >=0) {
            checkNode(currentNode, lowerRow, col, moveCost)
        }
    }

    //Checks to see if a given node is valid (i.e. not a block) and adds it to the open set
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
                    openSet.remove(neighbourNode)
                    openSet.add(neighbourNode)
                }
            }
        }
    }

    fun printMapSizeToConsole() {
        println("Map size: "+mapArray.size+" rows x "+mapArray[0].size+" columns")
    }

    fun printMapToConsole() {
        for (array in mapArray) {
            for (value in array) {
                print(value)
                if (value.equals(origin)) {
                    print("O    ")
                } else if (value.equals(destination)) {
                    print("D    ")
                } else if (value.isBlock) {
                    print("X    ")
                } else {
                    print("-    ")
                }
            }
            println()
        }
    }

}