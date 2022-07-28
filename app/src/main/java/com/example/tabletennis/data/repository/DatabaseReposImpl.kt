package com.example.tabletennis.data.repository

import com.example.tabletennis.data.dao.DatabaseDao
import com.example.tabletennis.data.ScoreDbEntity

class DatabaseReposImpl(private val dataBaseDao: DatabaseDao): DatabaseRepository {

    override suspend fun allGameResults(): List<ScoreDbEntity> {
        return dataBaseDao.getAllData()
    }

    override suspend fun insertGameData(scoreDbEntity: ScoreDbEntity) {
        dataBaseDao.insert(scoreDbEntity)
    }

    override suspend fun deleteGameData(scoreDbEntity: ScoreDbEntity) {
        dataBaseDao.delete(scoreDbEntity)
    }
}