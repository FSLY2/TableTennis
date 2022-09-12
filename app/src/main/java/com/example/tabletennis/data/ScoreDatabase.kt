package com.example.tabletennis.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tabletennis.data.converters.DateConverter
import com.example.tabletennis.data.dao.DatabaseDao
import com.example.tabletennis.data.entites.ScoreDbEntity

@Database(entities = [ScoreDbEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun getDbDao(): DatabaseDao
}