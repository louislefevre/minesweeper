package com.minesweeper.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minesweeper.app.game.Grid
import java.util.Date

@Entity(tableName = "session")
data class Session(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "grid_data")
    val grid: String,

    @ColumnInfo(name = "win")
    val isWin: Boolean,

    @ColumnInfo(name = "date")
    val datePlayed: Long,

    @ColumnInfo(name = "elapsed_time")
    val elapsedTime: Long,

    @ColumnInfo(name = "found_mines")
    val revealedMines: Int,

    @ColumnInfo(name = "total_mines")
    val totalMines: Int
)
