package com.example.tabletennis.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletennis.data.repository.DatabaseRepository
import com.example.tabletennis.models.GameDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel
@Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private val _allResults = MutableLiveData<List<GameDetails>>()
    val allResults: LiveData<List<GameDetails>>
        get() = _allResults

    fun insert(gameDetails: GameDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGameData(gameDetails)
//            _allResults.postValue(repository.getAllGameData())
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGameData(id)
        }
    }

    fun getAllResults() {
        viewModelScope.launch(Dispatchers.IO) {
            _allResults.postValue(repository.getAllGameData())
        }
    }
}