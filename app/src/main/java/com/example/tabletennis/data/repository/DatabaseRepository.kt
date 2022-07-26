package com.example.tabletennis.data.repository

import androidx.lifecycle.LiveData
import com.example.tabletennis.models.ScoreDbEntity

interface DatabaseRepository {

    val allGameResults: LiveData<List<ScoreDbEntity>>
    fun insertGameData(scoreDbEntity: ScoreDbEntity, onSuccess:() -> Unit)
    fun deleteGameData(scoreDbEntity: ScoreDbEntity, onSuccess:() -> Unit)
}