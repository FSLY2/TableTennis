package com.example.tabletennis.models

import android.os.Parcelable
import com.example.tabletennis.common.GameTime
import com.example.tabletennis.common.PlayerNumber
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GameDetails(
    val id: Int,
    val firstPlayer: Players,
    val secondPlayer: Players,
    var feed: PlayerNumber? = null,
    var winner: PlayerNumber? = null,
    var finalScore: Int = 0,
    var date: Date? = null,
    var gameTime: GameTime = GameTime.REGULAR_TIME
) : Parcelable