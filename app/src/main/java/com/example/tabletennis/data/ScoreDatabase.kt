package com.example.tabletennis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabletennis.data.dao.DatabaseDao
import com.example.tabletennis.models.ScoreDbEntity

@Database(entities = [ScoreDbEntity::class], version = 1)
abstract class ScoreDatabase: RoomDatabase() {

    abstract fun getDbDao(): DatabaseDao

    companion object {
        private var database: ScoreDatabase ?= null

        @Synchronized
        fun getDatabase(context: Context): ScoreDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    ScoreDatabase::class.java,
                    "game_table"
                ).build()
                database as ScoreDatabase
            } else {
                database as ScoreDatabase
            }
        }
    }
}