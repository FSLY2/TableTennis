package com.example.tabletennis.models

sealed class GameStatus {
    class Init(val gameDetail: GameDetails): GameStatus()
    class Resume(val gameDetail: GameDetails): GameStatus()
    class Finish(val gameDetail: GameDetails): GameStatus()
    object Cancel: GameStatus()
}