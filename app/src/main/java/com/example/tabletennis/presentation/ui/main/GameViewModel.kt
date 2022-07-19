package com.example.tabletennis.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.presentation.common.GameStatus
import com.example.tabletennis.presentation.common.ScoreEvent
import com.example.tabletennis.presentation.modela.Players

class GameViewModel: ViewModel() {
    private var _firstPlayer: Players.First? = null
    val firstPlayer: Players.First
        get() = _firstPlayer ?: Players.First("")

    private var _secondPlayer: Players.Second? = null
    val secondPlayer: Players.Second
        get() = _secondPlayer ?: Players.Second("")

    private val gameStatusLiveData = MutableLiveData<GameStatus>()
    val gameStatus: LiveData<GameStatus>
        get() = gameStatusLiveData

    // Creating players
    fun initPlayers(firstPlayerName: String, secondPlayerName: String) {
        _firstPlayer = Players.First(firstPlayerName)
        _secondPlayer = Players.Second(secondPlayerName)
        gameStatusLiveData.postValue(GameStatus.Init(firstPlayer, secondPlayer))
    }

    fun changePlayerScore(
        player: Players,
        event: ScoreEvent,
        callback: (Players) -> Unit
    ) {
        val updPlayer = when(player) {
            is Players.First -> {
                firstPlayer.score = changeScore(event, firstPlayer.score)
                firstPlayer
            }
            is Players.Second -> {
                secondPlayer.score = changeScore(event, secondPlayer.score)
                secondPlayer
            }
        }
        if (updPlayer.score in DEFAULT_SCORE..FINAL_SCORE) {
            callback.invoke(updPlayer)
        }
        //Check for a winner "Player 1"
        if (firstPlayer.score == FINAL_SCORE) {
            gameStatusLiveData.postValue(GameStatus.Finish(firstPlayer))
        }
        //Check for a winner "Player 2"
        if (secondPlayer.score == FINAL_SCORE) {
            gameStatusLiveData.postValue(GameStatus.Finish(secondPlayer))
        }
    }

    fun cancel() {
        gameStatusLiveData.postValue(GameStatus.Cancel)
    }

    private fun changeScore(event: ScoreEvent, score: Int): Int =
        when(event) {
            ScoreEvent.UP -> score + 1
            ScoreEvent.DOWN -> score - 1
        }

    companion object {
        private const val DEFAULT_SCORE = 0
        private const val FINAL_SCORE = 11
    }
}