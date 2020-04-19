package com.soen390.conumap.IndoorNavigation

import kotlin.math.abs

class Node (x: Int, y: Int) {
    var f: Int = 0
    var g: Int = 0
    var h: Int = 0
    var row: Int = y
    var col: Int = x
    var isBlock: Boolean = false
    var parent: Node? = null

    fun calculateH(finalNode: Node) {
        h = abs(finalNode.row - row) + abs(finalNode.col - col)
    }

    fun calculateF() {
        f = g + h
    }

    fun updateNode(currentNode: Node, cost: Int) {
        g = currentNode.g + cost
        parent = currentNode
        calculateF()
    }

    fun getX():Float{
        return col.toFloat()
    }

    fun getY():Float{
        return row.toFloat()
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
        return "Node("+col+","+row+")"
    }

    fun equals (otherNode: Node): Boolean {
        return this.row == otherNode.row && this.col == otherNode.col
    }

    fun makeBlock(block: Boolean) {
        isBlock = block
    }

}