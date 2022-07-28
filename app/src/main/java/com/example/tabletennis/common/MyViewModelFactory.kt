package com.example.tabletennis.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tabletennis.data.ScoreDatabase
import com.example.tabletennis.data.repository.DatabaseReposImpl
import com.example.tabletennis.ui.DatabaseViewModel

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val daoDb = ScoreDatabase.getDatabase(context).getDbDao()
        val repository = DatabaseReposImpl(daoDb)
        return DatabaseViewModel(repository) as T
    }
}