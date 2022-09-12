package com.example.tabletennis.di

import android.content.Context
import androidx.room.Room
import com.example.tabletennis.data.ScoreDatabase
import com.example.tabletennis.data.dao.DatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabaseDao(scoreDatabase: ScoreDatabase): DatabaseDao = scoreDatabase.getDbDao()

    @Provides
    @Singleton
    fun provideScoreDatabase(@ApplicationContext context: Context): ScoreDatabase {
        return Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "game_table"
        ).build()
    }
}