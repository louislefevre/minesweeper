package com.minesweeper.app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Session::class], version = 1)
abstract class SessionDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
}
