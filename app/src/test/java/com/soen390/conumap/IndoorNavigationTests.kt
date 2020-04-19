package com.soen390.conumap

import com.nhaarman.mockitokotlin2.whenever
import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.IndoorNavigation.NodeComparator
import com.soen390.conumap.IndoorNavigation.Pathfinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class IndoorNavigationTests {
    // Node class tests.
    var testNode1: Node = Node(0, 0)
    var testNode2: Node = Node(5, 5)

    @Test
    fun calculateHTest() {
        testNode1.calculateH(testNode2)
        testNode2.calculateH(testNode1)
        assertEquals(10, testNode1.h)
        assertEquals(10, testNode2.h)
    }

    @Test
    fun calculateFTest() {
        testNode1.calculateF()
        assertEquals(0, testNode1.f)

        testNode2.calculateF()
        assertEquals(0, testNode2.f)
    }

    @Test
    fun updateNodeTest() {
        testNode1.updateNode(testNode2, 10)
        assertEquals(10, testNode1.g)
        assertEquals(testNode2, testNode1.parent)
        assertEquals(10, testNode1.f)
    }

    @Test
    fun checkAlternativeTest() {
        assertFalse(testNode1.checkAlternative(testNode2, 10))
    }

    @Test
    fun toStringTest() {
        assertEquals("Node(0,0)", testNode1.toString())
        assertEquals("Node(5,5)", testNode2.toString())
    }

    @Test
    fun equalsTest() {
        assertFalse(testNode1.equals(testNode2))
    }

    @Test
    fun makeBlockTest() {
        testNode1.makeBlock(true)
        testNode2.makeBlock(false)
        assert(testNode1.isBlock)
        assertFalse(testNode2.isBlock)
    }

    // NodeComparator tests.
    @Test
    fun nodeComparatorTest() {
        NodeComparator()

        testNode2.g = 5
        testNode2.calculateF()
        assertEquals(0, NodeComparator.compare(testNode1, testNode1))
        assertEquals(-5, NodeComparator.compare(testNode1, testNode2))
        assertEquals(5, NodeComparator.compare(testNode2, testNode1))
    }

    // Pathfinding tests.
    var origin: Node = Node(2, 1)
    var destination: Node = Node(7, 7)
    var row = 8
    var col = 8
    var pfTest: Pathfinding = Pathfinding(row, col, origin, destination)
    var blockRow = arrayListOf<Int>(1,2,3,4,5,6,7,7,6,5,3,3,3)
    var blockCol = arrayListOf<Int>(3,3,3,3,3,3,3,6,6,6,5,6,7)
    var blockArray = arrayOf<ArrayList<Int>>(blockRow, blockCol)

    @Test
    fun findPathTest() {
        pfTest.loadMap()
        pfTest.loadBlocks(blockArray)

        var path = pfTest.findPath()

        var expected: MutableList<Node> = mutableListOf()

        expected.add(Node(2,1))
        expected.add(Node(2, 0))
        expected.add(Node(3, 0))
        expected.add(Node(4, 0))
        expected.add(Node(4, 1))
        expected.add(Node(4, 2))
        expected.add(Node(4, 3))
        expected.add(Node(4, 4))
        expected.add(Node(5, 4))
        expected.add(Node(6, 4))
        expected.add(Node(7, 4))
        expected.add(Node(7, 5))
        expected.add(Node(7, 6))
        expected.add(Node(7, 7))

        for(i in 0 until path.size) {
            assert(expected[i].equals(path[i]))
        }

        pfTest.printMapSizeToConsole()
        pfTest.printMapToConsole()
    }
}