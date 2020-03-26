package com.soen390.conumap.IndoorNavigation

class Node (row: Int, col: Int) {
    var f: Int = 0
    var g: Int = 0
    var h: Int = 0
    var row: Int = row
    var col: Int = col
    var isBlock: Boolean = false
    var parent: Node? = null

    fun calculateH(finalNode: Node) {
        h = (finalNode.row - row) + (finalNode.col - col)
    }

    fun calculateF() {
        f = g + h
    }

    fun updateNode(currentNode: Node, cost: Int) {
        g = currentNode.g + cost
        parent = currentNode
        calculateF()
    }

    //Method checks to see if there exists alternative path better than current one
    fun checkAlternative(currentNode: Node, cost: Int): Boolean {
        var newG: Int = currentNode.g + cost
        if (newG < g) {
            updateNode(currentNode, cost);
            return true
        }
        return false
    }

    override fun toString(): String {
        return "Node("+row+","+col+")"
    }

    fun equals (otherNode: Node): Boolean {
        return this.row == otherNode.row && this.col == otherNode.col
    }

    fun makeBlock(block: Boolean) {
        isBlock = block
    }

}