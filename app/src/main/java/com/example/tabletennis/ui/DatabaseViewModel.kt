package com.example.tabletennis.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletennis.data.repository.DatabaseRepository
import com.example.tabletennis.data.ScoreDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val _allResults = MutableLiveData<List<ScoreDbEntity>>()
    val allResults: LiveData<List<ScoreDbEntity>>
        get() = _allResults

    fun insert(scoreDbEntity: ScoreDbEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGameData(scoreDbEntity)
            _allResults.postValue(repository.allGameResults())
        }
    }

    fun deleteItem(scoreDbEntity: ScoreDbEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGameData(scoreDbEntity)
        }
    }

    fun getAllResults() {
        viewModelScope.launch(Dispatchers.IO) {
            _allResults.postValue(repository.allGameResults())
        }
    }
}