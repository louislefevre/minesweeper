package com.minesweeper.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(session: Session)

    @Update
    suspend fun update(session: Session)

    @Delete
    suspend fun delete(session: Session)

    @Query("SELECT * FROM session WHERE id = :id")
    suspend fun getById(id: Int): Session?

    @Query("SELECT * FROM session ORDER BY date DESC")
    fun getAll(): Flow<List<Session>>
}
