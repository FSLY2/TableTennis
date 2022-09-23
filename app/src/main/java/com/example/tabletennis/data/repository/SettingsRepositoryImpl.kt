package com.example.tabletennis.data.repository

import com.example.tabletennis.common.Preferences
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {
    override var finalScore: Int
        get() = Preferences.load(FINAL_SCORE, 11)
        set(value) = Preferences.save(FINAL_SCORE, value)

    companion object {
        private const val FINAL_SCORE = "FINAL_SCORE"
    }
}