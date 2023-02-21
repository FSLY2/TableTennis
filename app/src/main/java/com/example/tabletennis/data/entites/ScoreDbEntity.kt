package com.example.tabletennis.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game_table")
data class ScoreDbEntity(

    @PrimaryKey(autoGenerate = true)
    var gameId: Int,
    var date: Date,
    val pNameOne: String,
    val pNameTwo: String,
    val pScoreOne: Int,
    val pScoreTwo: Int,
    val winner: String?,
    var pPhotoOne: String,
    var pPhotoTwo: String
)