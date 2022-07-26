package com.example.tabletennis.data.repository

import androidx.lifecycle.LiveData
import com.example.tabletennis.data.dao.DatabaseDao
import com.example.tabletennis.models.ScoreDbEntity

class DatabaseReposImpl(private val dataBaseDao: DatabaseDao): DatabaseRepository {

    override val allGameResults: LiveData<List<ScoreDbEntity>>
        get() = dataBaseDao.getAllData()

    override fun insertGameData(scoreDbEntity: ScoreDbEntity, onSuccess: () -> Unit) {
        dataBaseDao.insert(scoreDbEntity)
        onSuccess()
    }

    override fun deleteGameData(scoreDbEntity: ScoreDbEntity, onSuccess: () -> Unit) {
        dataBaseDao.delete(scoreDbEntity)
        onSuccess()
    }
}