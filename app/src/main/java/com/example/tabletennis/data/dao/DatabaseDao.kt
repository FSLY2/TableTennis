package com.example.tabletennis.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tabletennis.data.ScoreDbEntity

@Dao
interface DatabaseDao {

    @Insert
    fun insert(scoreDbEntity: ScoreDbEntity)

    @Delete
    fun delete(scoreDbEntity: ScoreDbEntity)

    @Query("SELECT * from game_table")
    fun getAllData(): List<ScoreDbEntity>
}