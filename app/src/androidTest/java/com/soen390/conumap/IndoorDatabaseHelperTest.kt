package com.soen390.conumap

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.soen390.conumap.IndoorNavigation.IndoorDatabaseHelper
import com.soen390.conumap.IndoorNavigation.Node
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// SYSTEM TEST ST4-1.1 (Test Database Functionality)
// NOTE: These tests are purely for testing database functionality and not for testing UI elements.
// As such there is no need to open the application like in other system tests.
// However, the DB tests still need to be run on the device.
// So when nothing happens on the device during this test, don't worry, that's normal!

@RunWith(AndroidJUnit4::class)
class IndoorDatabaseHelperTest {
    var ctx = ApplicationProvider.getApplicationContext<Context>()
    var testDb = IndoorDatabaseHelper(ctx)

    @Before
    fun setUpDatabase() {
        // Clear the database in case it is populated from being used on device.
        testDb.emptyDatabaseContents()

        // Add some test values to the database
        testDb.insertData("H", "9", "907", "H-907", "12", "12")
        testDb.insertData("H", "8", "820", "H-820", "5", "6")
        testDb.insertData("H", "9", "937", "H-937", "14", "1")
        testDb.insertData("H", "8", "815", "H-815", "20", "3")
        testDb.insertData("H", "9", "9WF", "Water Fountain", "2", "6")
        testDb.insertData("H", "9", "9MW", "Men's Washroom", "7", "8")
    }

    @After
    fun tearDownDatabase() {
        // Clear the database so the test values will not be used on next time app is run. The next time
        // app is run, it should detect that the DB is empty and repopulate it with the real values.
        testDb.emptyDatabaseContents()
    }

    @Test
    fun insertDataTest() {
        var countBefore = testDb.getNumberOfRows()
        assertEquals(6, countBefore)

        // Make sure the function returns true upon successful insertion.
        assert(testDb.insertData("H", "4", "420", "H-420", "6", "9"))

        // Make sure there is an additional entry in the DB.
        assertEquals(countBefore + 1, testDb.getNumberOfRows())

        // Make sure the item we entered is present.
        assert(Node(6, 9).equals(testDb.getRoomCoordinates("H-420")))

        // Same tests but begin with no records.
        testDb.emptyDatabaseContents()
        countBefore = 0
        assertEquals(countBefore, testDb.getNumberOfRows())

        assert(testDb.insertData("H", "8", "815", "H-815", "20", "3"))
        assertEquals(countBefore + 1, testDb.getNumberOfRows())
        assert(Node(20, 3).equals(testDb.getRoomCoordinates("H-815")))
    }

    @Test
    fun getRoomCoordinatesTest() {
        // Test existing room.
        assert(Node(12, 12).equals(testDb.getRoomCoordinates("H-907")))

        // Test non-existing room.
        assert(Node(-1, -1).equals(testDb.getRoomCoordinates("H-123")))

        // Test non-existing room on empty DB.
        testDb.emptyDatabaseContents()
        assert(Node(-1, -1).equals(testDb.getRoomCoordinates("H-937")))
    }

    @Test
    fun getPOICoordinatesTest() {
        // Test existing POI.
        assert(Node(2, 6).equals(testDb.getPOICoordinates("Water Fountain", 9)))

        // Test non-existing POI.
        assert(Node(-1, -1).equals(testDb.getPOICoordinates("Bedbug Zoo", 1)))

        // Test non-existing POI on empty DB.
        testDb.emptyDatabaseContents()
        assert(Node(-1, -1).equals(testDb.getPOICoordinates("Men's Washroom", 9)))
    }

    @Test
    fun getNumberOfRowsTest() {
        // Test correct for initial DB.
        assertEquals(6, testDb.getNumberOfRows())

        // Test after inserting new data.
        testDb.insertData("H", "4", "421", "H-421", "6", "10")
        testDb.insertData("H", "4", "420", "H-420", "6", "9")
        assertEquals(8, testDb.getNumberOfRows())

        // Test correct count after deleting contents.
        testDb.emptyDatabaseContents()
        assertEquals(0, testDb.getNumberOfRows())
    }

    @Test
    fun isEmptyTest() {
        // Non-empty case.
        assertFalse(testDb.isEmpty())

        // Empty case.
        testDb.emptyDatabaseContents()
        assert(testDb.isEmpty())
    }

    @Test
    fun emptyDatabaseContentsTest() {
        // Make sure returns true when successfully emptied.
        assert(testDb.emptyDatabaseContents())

        // Make sure there are no records.
        assertEquals(0, testDb.getNumberOfRows())

        // Test emptying an already empty DB.
        assert(testDb.emptyDatabaseContents())
        assertEquals(0, testDb.getNumberOfRows())
    }
}
