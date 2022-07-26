package com.example.tabletennis.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "game_table")
class ScoreDbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Int = 0,

//    @ColumnInfo(name = "date")
//    var date: Int,

    @ColumnInfo(name = "first_player")
    val pNameOne: String = "",

    @ColumnInfo(name = "second_player")
    val pNameTwo: String = "",

    @ColumnInfo(name = "score_first_player")
    val pScoreOne: Int = 0,

    @ColumnInfo(name = "score_second_player")
    val pScoreTwo: Int = 0,

    @ColumnInfo(name = "winner")
    val winner: String = ""
)