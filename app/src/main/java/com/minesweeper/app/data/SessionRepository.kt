package com.minesweeper.app.data

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insert(session: Session)

    suspend fun update(session: Session)

    suspend fun delete(session: Session)

    suspend fun getById(id: Int): Session?

    fun getAll(): Flow<List<Session>>
}
