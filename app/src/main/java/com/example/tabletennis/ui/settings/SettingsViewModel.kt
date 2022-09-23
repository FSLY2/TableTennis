package com.example.tabletennis.ui.settings

import androidx.lifecycle.ViewModel
import com.example.tabletennis.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingRepos: SettingsRepository) : ViewModel() {
    var finalScore: Int = 0
        get() = settingRepos.finalScore

        set(value) {
            settingRepos.finalScore = value
            field = value
        }
}