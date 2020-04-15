package com.soen390.conumap.IndoorNavigation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    constructor(ctx: Context) : super(ctx, DATABASE_NAME, null, 1) {
        var db: SQLiteDatabase = this.writableDatabase
    }

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

}