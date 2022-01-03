package com.minesweeper.app.data

import androidx.room.TypeConverter
import com.minesweeper.app.game.Grid
import java.util.Date

class Converters {

    @TypeConverter
    fun timestampToDatePlayed(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun datePlayedToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun stringToGrid(value: String): Grid {
        TODO()
    }

    @TypeConverter
    fun gridToString(grid: Grid): String {
        TODO()
    }
}
