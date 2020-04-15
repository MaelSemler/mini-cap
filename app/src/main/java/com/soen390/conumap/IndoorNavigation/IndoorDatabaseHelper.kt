package com.soen390.conumap.IndoorNavigation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// IndoorDatabase will be used to store node coordinates of rooms. When user searches for a room,
// the database will be queried and return the coordinates of the node, which will be used with the
// pathfinding algorithm.
class IndoorDatabaseHelper: SQLiteOpenHelper {
    companion object Database {
        const val DATABASE_NAME: String = "Indoor.db"
        const val TABLE_NAME: String = "Indoor_Table"
        const val COL_ID: String = "ID"
        const val COL_BC: String = "BUILDING_CODE"
        const val COL_FN: String = "FLOOR_NUMBER"
        const val COL_RN: String = "ROOM_NUMBER"
        const val COL_RC: String = "ROOM_CODE"

        const val COL_NX: String = "NODE_X_POS"
        const val COL_NY: String = "NODE_Y_POS"
    }

    constructor(ctx: Context) : super(ctx, DATABASE_NAME, null, 1)

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_BC TEXT, " +
                "$COL_FN INTEGER, " +
                "$COL_RN INTEGER, " +
                "$COL_RC TEXT, " +
                "$COL_NX INTEGER, " +
                "$COL_NY INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Used to populate database. Returns true if insertion was successful, false otherwise.
    fun insertData(bc: String, fn: String, rn: String, rc: String, nx: String, ny: String): Boolean {
        var db: SQLiteDatabase = this.writableDatabase

        // Holds data that we want to insert.
        var values = ContentValues()
        values.put(COL_BC, bc)
        values.put(COL_FN, fn)
        values.put(COL_RN, rn)
        values.put(COL_RC, rc)
        values.put(COL_NX, nx)
        values.put(COL_NY, ny)

        // Insert it, will return -1 if error.
        return db.insert(TABLE_NAME, null, values) != (-1).toLong()
    }

    // To check how many rows are in the database currently.
    fun getNumberOfRows(): Int {
        val db: SQLiteDatabase = this.writableDatabase

        val contents: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val count = contents.count
        contents.close()
        
        return count
    }

    // To verify if database has been populated or not. Returns true if empty, false if non-empty.
    fun isEmpty(): Boolean {
        return getNumberOfRows() == 0
    }

    // Deletes everything in the database. Return true if rows deleted, else false.
    fun emptyDatabaseContents(): Boolean {
        val db: SQLiteDatabase = this.writableDatabase

        // Delete everything, will return 0 if nothing deleted.
        return db.delete(TABLE_NAME, null, null) > 0
    }

    // Prints database contents to the console for testing purposes.
    fun printDatabaseContents() {
        val db: SQLiteDatabase = this.writableDatabase

        val contents: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // Print all data or show that it is empty.
        if(contents.count > 0) {
            Log.i("IndoorDatabase", "Count is " + contents.count + ". Current database contents are:\n" +
                    "$COL_ID\t$COL_BC\t$COL_FN\t$COL_RN\t$COL_RC\t$COL_NX\t$COL_NY")
            while(contents.moveToNext()) {
                Log.i("IndoorDatabase", contents.getString(0) + "\t" + // Print ID.
                        contents.getString(1) + "\t\t\t\t" +                     // Print Building Code.
                        contents.getString(2) + "\t\t\t\t" +                     // Print Floor Number.
                        contents.getString(3) + "\t\t\t" +                       // Print Room Number.
                        contents.getString(4) + "\t\t" +                       // Print Room Code.
                        contents.getString(5) + "\t\t" +                       // Print Node X Pos.
                        contents.getString(6) + "\n"                             // Print Node Y Pos.
                )
            }
        } else {
            Log.i("IndoorDatabase", "Count is " + contents.count + ". Database is currently empty.")
        }

        contents.close()
    }

}