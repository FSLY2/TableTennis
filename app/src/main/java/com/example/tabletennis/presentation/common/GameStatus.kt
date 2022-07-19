package com.example.tabletennis.presentation.common

import com.example.tabletennis.presentation.modela.Players

sealed class GameStatus {
    class Init(val firstPlayer: Players.First, val secondPlayer: Players.Second): GameStatus()
    class Resume(val player: Players): GameStatus()
    class Finish(val winner: Players): GameStatus()
    object Cancel: GameStatus()
}