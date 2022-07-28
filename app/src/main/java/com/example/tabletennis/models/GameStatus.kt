package com.example.tabletennis.models

sealed class GameStatus {
    class Init(val firstPlayer: Players.First, val secondPlayer: Players.Second): GameStatus()
    class Resume(val player: Players): GameStatus()
    class Finish(val winner: Players): GameStatus()
    object Cancel: GameStatus()
}