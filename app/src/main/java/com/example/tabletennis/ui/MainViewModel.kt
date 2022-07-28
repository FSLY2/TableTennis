package com.example.tabletennis.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.models.GameStatus
import com.example.tabletennis.models.Players
import com.example.tabletennis.models.ScoreEvent

class MainViewModel() : ViewModel() {

    private var _firstPlayer: Players.First? = null
    val firstPlayer: Players.First
        get() = _firstPlayer ?: Players.First("")

    private var _secondPlayer: Players.Second? = null
    val secondPlayer: Players.Second
        get() = _secondPlayer ?: Players.Second("")

    private val gameStatusLiveData = MutableLiveData<GameStatus>()
    val gameStatus: LiveData<GameStatus>
        get() = gameStatusLiveData

    //Init player name
    fun initPlayers(firstPlayerName: String, secondPlayerName: String) {
        _firstPlayer = Players.First(firstPlayerName)
        _secondPlayer = Players.Second(secondPlayerName)
        gameStatusLiveData.postValue(GameStatus.Init(firstPlayer, secondPlayer))
    }

    //Change Player score
    fun changePlayerScore(player: Players, event: ScoreEvent) {
        val updPlayer = when (player) {
            is Players.First -> {
                firstPlayer.score = changeScore(event, firstPlayer.score)
                firstPlayer
            }
            is Players.Second -> {
                secondPlayer.score = changeScore(event, secondPlayer.score)
                secondPlayer
            }
        }
        //Check for winner
        when (updPlayer.score) {
            in DEFAULT_SCORE until FINAL_SCORE ->
                gameStatusLiveData.postValue(GameStatus.Resume(updPlayer))
            FINAL_SCORE -> gameStatusLiveData.postValue(GameStatus.Finish(updPlayer))
        }
    }

    //Logic of the changing score
    private fun changeScore(event: ScoreEvent, score: Int): Int =
        when (event) {
            ScoreEvent.UP -> score + 1
            ScoreEvent.DOWN -> score - 1
        }

    fun cancel() {
        gameStatusLiveData.postValue(GameStatus.Cancel)
    }

    companion object {
        private const val DEFAULT_SCORE = 0
        private const val FINAL_SCORE = 11
    }
}