package com.soen390.conumap.IndoorNavigation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File

// IndoorDatabase will be used to store node coordinates of rooms. When user searches for a room, the
// database will be queried and return a Node, which can be used with the pathfinding algorithm.
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
        val db: SQLiteDatabase = this.writableDatabase

        // Holds data that we want to insert.
        val values = ContentValues()
        values.put(COL_BC, bc)
        values.put(COL_FN, fn)
        values.put(COL_RN, rn)
        values.put(COL_RC, rc)
        values.put(COL_NX, nx)
        values.put(COL_NY, ny)

        // Insert it, db.insert will return -1 if error.
        return db.insert(TABLE_NAME, null, values) != (-1).toLong()
    }

    // Specify a room code and will return an array with the node coordinates of that room by searching the
    // database for a match. Returns a Node of the room coordinates. Node(-1, -1) means room not found.
    fun getRoomCoordinates(roomCode: String): Node {
        val db: SQLiteDatabase = this.writableDatabase

        // Get the NODE_X_POS and NODE_Y_POS for the matching room code and store it.
        val coords: Cursor = db.rawQuery("SELECT $COL_NX, $COL_NY " +
                "FROM $TABLE_NAME WHERE $COL_RC = ?", arrayOf(roomCode)
        )

        return if(coords.count > 0) {
            // Found a result, return it as a node.
            coords.moveToFirst()
            val result = Node(coords.getString(0).toInt(), coords.getString(1).toInt())
            coords.close()

            result
        } else {
            // Not found, return Node with x and y -1, -1.
            Log.e("IndoorDatabase", "Room $roomCode not found. Returning Node(-1, -1).")
            coords.close()

            Node(-1, -1)
        }
    }

    // Works in the same was as getRoomCoordinates but also checks that the floor matches.
    fun getPOICoordinates(roomCode: String, floorNumber: Int): Node {
        val db: SQLiteDatabase = this.writableDatabase

        val coords: Cursor = db.rawQuery("SELECT $COL_NX, $COL_NY " +
                "FROM $TABLE_NAME WHERE $COL_RC = ? AND $COL_FN = ?", arrayOf(roomCode, floorNumber.toString())
        )

        // Return result if we find one.
        return if(coords.count > 0) {
            coords.moveToFirst()
            val result = Node(coords.getString(0).toInt(), coords.getString(1).toInt())
            coords.close()

            result
        } else {
            // Not found, return Node with x and y -1, -1.
            Log.e("IndoorDatabase", "POI $roomCode on floor $floorNumber not found. Returning Node(-1, -1).")
            coords.close()

            Node(-1, -1)
        }
    }

    // Returns an array of Strings containing all room codes in the DB, sorted.
    fun getAllRoomCodes(): List<String> {
        val db: SQLiteDatabase = this.writableDatabase
        var roomsInDb = mutableListOf<String>()

        // Query DB for all room codes.
        val roomCodes: Cursor = db.rawQuery("SELECT $COL_RC FROM $TABLE_NAME",null)

        // Add to roomsInDb.
        while(roomCodes.moveToNext()) {
            roomsInDb.add(roomCodes.getString(0))
        }
        roomCodes.close()

        writeToTextFile(roomsInDb)

        // Sort, turn to array, and return.
        return roomsInDb.toList().sorted()
    }

    fun writeToTextFile(roomsInDb: MutableList<String>){
        val fileName = "/src/main/assets/ClassRoom.txt"
        val file = File(fileName)

        file.printWriter().use { out ->

            for(room in roomsInDb){
                out.println(room)
            }
        }


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

        // Delete everything, db.delete will return 0 if nothing deleted.
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
                        contents.getString(4) + "\t\t" +                         // Print Room Code.
                        contents.getString(5) + "\t\t\t" +                       // Print Node X Pos.
                        contents.getString(6) + "\n"                             // Print Node Y Pos.
                )
            }
        } else {
            Log.i("IndoorDatabase", "Count is " + contents.count + ". Database is currently empty.")
        }

        contents.close()
    }
}
