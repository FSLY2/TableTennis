package com.example.tabletennis.data.repository

import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.data.dao.DatabaseDao
import com.example.tabletennis.data.entites.ScoreDbEntity
import com.example.tabletennis.models.GameDetails
import com.example.tabletennis.models.Players
import java.util.*
import javax.inject.Inject

class DatabaseReposImpl @Inject constructor(private val dataBaseDao: DatabaseDao) :
    DatabaseRepository {

    override suspend fun getAllGameData(): List<GameDetails> {
        val resultGameList = dataBaseDao.getAllData()
        return resultGameList.map {
            val winner =
                if (it.pScoreOne > it.pScoreTwo) PlayerNumber.FIRST
                else PlayerNumber.SECOND
            GameDetails(
                id = it.gameId,
                firstPlayer = Players.First(it.pNameOne, it.pScoreOne),
                secondPlayer = Players.Second(it.pNameTwo, it.pScoreTwo),
                winner = winner,
                date = it.date
            )
        }
    }

    override suspend fun insertGameData(gameDetails: GameDetails) {
        with(gameDetails) {
            val scoreDbEntity = ScoreDbEntity(
                gameId = 0,
                date = Calendar.getInstance().time,
                pNameOne = firstPlayer.pName,
                pNameTwo = secondPlayer.pName,
                pScoreOne = firstPlayer.score,
                pScoreTwo = secondPlayer.score,
                pPhotoOne = firstPlayer.photo.toString(),
                pPhotoTwo = secondPlayer.photo.toString(),
                winner = when (winner) {
                    PlayerNumber.FIRST -> firstPlayer.pName
                    PlayerNumber.SECOND -> secondPlayer.pName
                    else -> null
                }
            )
            dataBaseDao.insert(scoreDbEntity)
        }
    }

    override suspend fun deleteGameData(id: Int) {
        dataBaseDao.delete(id)
    }
}