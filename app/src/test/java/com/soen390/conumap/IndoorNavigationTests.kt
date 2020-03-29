package com.soen390.conumap

import com.soen390.conumap.IndoorNavigation.Node
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

}