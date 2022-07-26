package com.example.tabletennis.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletennis.models.ScoreDbEntity

@Dao
interface DatabaseDao {

    @Insert
    fun insert(scoreDbEntity: ScoreDbEntity)

    @Delete
    fun delete(scoreDbEntity: ScoreDbEntity)

    @Query("SELECT * from game_table")
    fun getAllData(): LiveData<List<ScoreDbEntity>>
}