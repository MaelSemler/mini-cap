package com.soen390.conumap

import android.content.Context
import com.soen390.conumap.IndoorNavigation.IndoorDatabaseHelper
import org.junit.Test
import org.mockito.Mockito.mock
import org.robolectric.Robolectric




class IndoorDatabaseHelperTests {
    // Testing IndoorDatabaseHelper functionality.
    var ctx: Context = mock(Context::class.java)
    var testDb = IndoorDatabaseHelper(ctx)

    @Test
    fun testTest() {
        testDb.printDatabaseContents()
        testDb.insertData("", "", "", "", "", "")
        testDb.printDatabaseContents()
    }
}