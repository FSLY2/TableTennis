package com.example.tabletennis.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletennis.common.GameTime
import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.data.repository.DatabaseRepository
import com.example.tabletennis.data.repository.SettingsRepository
import com.example.tabletennis.models.GameDetails
import com.example.tabletennis.models.GameStatus
import com.example.tabletennis.models.Players
import com.example.tabletennis.models.ScoreEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DatabaseRepository, private val settingRepos: SettingsRepository) : ViewModel() {

    private var gameDetail: GameDetails? = null
    private var feedCounter: Int = 0

    private val gameStatusLiveData = MutableLiveData<GameStatus>()
    val gameStatus: LiveData<GameStatus>
        get() = gameStatusLiveData


    fun initGameDetails() {
        gameDetail = GameDetails(
            id = 0,
            firstPlayer = Players.First(),
            secondPlayer = Players.Second(),
            finalScore = settingRepos.finalScore
        )
        gameStatusLiveData.postValue(GameStatus.Init(gameDetail!!))
    }

    fun saveGame(gameDetails: GameDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGameData(gameDetails)
        }
    }

    //Init player name
    fun setNameForGamer(firstPlayerName: String? = null, secondPlayerName: String? = null) {
        gameDetail?.let { gameDetail ->
            firstPlayerName?.let { gameDetail.firstPlayer.pName = it }
            secondPlayerName?.let { gameDetail.secondPlayer.pName = it }
        }
    }

    //Change Player score
    fun changePlayerScore(playerNumber: PlayerNumber, event: ScoreEvent, isFeed: Boolean = true) {
        gameDetail?.apply {
            val updPlayer: Players = when (playerNumber) {
                PlayerNumber.FIRST -> firstPlayer.apply { score = changeScore(event, score) }
                PlayerNumber.SECOND -> secondPlayer.apply { score = changeScore(event, score) }
            }

            val anotherPlayerScore = when (updPlayer) {
                is Players.First -> secondPlayer.score
                is Players.Second -> firstPlayer.score
            }

            gameTime = getGameTime(updPlayer.score, anotherPlayerScore, finalScore)

            if (isFeed)
                feed = changeFeed(playerNumber, gameTime.feed)

            val isRegularTimeWinner =
                gameTime == GameTime.REGULAR_TIME && updPlayer.score == finalScore

            val isOvertimeWinner =
                gameTime == GameTime.OVERTIME && (updPlayer.score - anotherPlayerScore) == 2

            //Check for winner
            if (isRegularTimeWinner || isOvertimeWinner) {
                winner = playerNumber
                gameStatusLiveData.postValue(GameStatus.Finish(this))
            } else {
                gameStatusLiveData.postValue(GameStatus.Resume(this))
            }
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

    //Feed change method
    private fun changeFeed(feed: PlayerNumber, ratio: Int): PlayerNumber {
        val currentFeed = gameDetail?.feed ?: feed
        return if (feedCounter >= ratio) {
            feedCounter = 0
            when (currentFeed) {
                PlayerNumber.FIRST -> PlayerNumber.SECOND
                PlayerNumber.SECOND -> PlayerNumber.FIRST
            }
        } else {
            feedCounter++
            currentFeed
        }
    }

    //Check for extra rounds
    private fun getGameTime(firstScore: Int, secondScore: Int, winScore: Int): GameTime {
        return if (firstScore >= (winScore - 1) && secondScore >= (winScore - 1)) GameTime.OVERTIME
        else GameTime.REGULAR_TIME
    }

    companion object {
        private const val FINAL_SCORE = 11
        private const val FINAL_SCORE_LONG_PART = 21
    }
}