package com.example.tabletennis.data.repository

import com.example.tabletennis.data.ScoreDbEntity

interface DatabaseRepository {

    suspend fun allGameResults(): List<ScoreDbEntity>
    suspend fun insertGameData(scoreDbEntity: ScoreDbEntity)
    suspend fun deleteGameData(scoreDbEntity: ScoreDbEntity)
}