package com.minesweeper.app.data

import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(private val dao: SessionDao) : SessionRepository {

    override suspend fun insert(session: Session) {
        dao.insert(session)
    }

    override suspend fun update(session: Session) {
        dao.update(session)
    }

    override suspend fun delete(session: Session) {
        dao.delete(session)
    }

    override suspend fun getById(id: Int): Session? {
        return dao.getById(id)
    }

    override fun getAll(): Flow<List<Session>> {
        return dao.getAll()
    }
}
