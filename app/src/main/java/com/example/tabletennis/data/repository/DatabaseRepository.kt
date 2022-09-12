package com.example.tabletennis.data.repository

import com.example.tabletennis.models.GameDetails

interface DatabaseRepository {

    suspend fun getAllGameData(): List<GameDetails>
    suspend fun insertGameData(gameDetails: GameDetails)
    suspend fun deleteGameData(id: Int)
}