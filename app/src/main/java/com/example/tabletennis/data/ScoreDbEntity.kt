package com.example.tabletennis.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "game_table")
data class ScoreDbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var gameId: Int? = null,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "first_player")
    val pNameOne: String,

    @ColumnInfo(name = "second_player")
    val pNameTwo: String,

    @ColumnInfo(name = "score_first_player")
    val pScoreOne: String,

    @ColumnInfo(name = "score_second_player")
    val pScoreTwo: String,

    @ColumnInfo(name = "winner")
    val winner: String
)