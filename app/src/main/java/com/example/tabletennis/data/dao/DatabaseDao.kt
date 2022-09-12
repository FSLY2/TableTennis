package com.example.tabletennis.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tabletennis.data.entites.ScoreDbEntity
import com.example.tabletennis.models.GameDetails

@Dao
interface DatabaseDao {

    @Insert
    fun insert(scoreDbEntity: ScoreDbEntity)

    @Query("DELETE FROM game_table WHERE :gameId")
    fun delete(gameId: Int)

    @Query("SELECT * FROM game_table")
    fun getAllData(): List<ScoreDbEntity>
}