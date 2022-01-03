package com.minesweeper.app.di

import android.app.Application
import androidx.room.Room
import com.minesweeper.app.data.SessionDatabase
import com.minesweeper.app.data.SessionRepository
import com.minesweeper.app.data.SessionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionDatabase(app: Application): SessionDatabase =
        Room.databaseBuilder(app, SessionDatabase::class.java, "session_db").build()

    @Provides
    @Singleton
    fun provideSessionRepository(database: SessionDatabase): SessionRepository =
        SessionRepositoryImpl(database.sessionDao())
}
