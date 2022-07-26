package com.example.tabletennis.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tabletennis.data.ScoreDatabase
import com.example.tabletennis.data.repository.DatabaseReposImpl
import com.example.tabletennis.data.repository.DatabaseRepository
import com.example.tabletennis.models.ScoreDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application): AndroidViewModel(application) {

    private val context = application
    private lateinit var repository: DatabaseRepository

    fun initDatabase() {
        val daoDb = ScoreDatabase.getDatabase(context).getDbDao()
        repository = DatabaseReposImpl(daoDb)
    }

    fun insert(scoreDbEntity: ScoreDbEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertGameData(scoreDbEntity) {
                onSuccess()
            }
        }
    }

    fun getAllResults(): LiveData<List<ScoreDbEntity>> {
        return repository.allGameResults
    }
}