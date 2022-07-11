package com.example.tabletennis.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.databinding.FragmentMainGameBinding

class MainGameFragment : Fragment() {

    private lateinit var binding: FragmentMainGameBinding
    private val args: MainGameFragmentArgs by navArgs()

    private lateinit var gamerOne: String
    private lateinit var gamerTwo: String

    private var firstCounter: Int = DEFAULT_SCORE
    private var secondCounter: Int = DEFAULT_SCORE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNames()
        initScore()
        gameScore()
    }

    //Method of assigning player names in textview
    private fun initNames() {
        gamerOne = args.gamerOne
        gamerTwo = args.gamerTwo
        binding.tvFirstPlayerName.text = gamerOne
        binding.tvSecondPlayerName.text = gamerTwo
    }

    //Method of assigning default score in textview
    private fun initScore() {
        binding.tvScorePlayerOne.text = firstCounter.toString()
        binding.tvScorePlayerTwo.text = secondCounter.toString()
    }

    //Implementation of UP/DOWN score buttons for both players
    private fun gameScore() {
        // Player 1
        binding.bFirstUpScore.setOnClickListener {
            changeScore(ScoreEvent.UP, binding.tvScorePlayerOne, firstCounter) {
                firstCounter++
            }
        }

        binding.bFirstDownScore.setOnClickListener {
            changeScore(ScoreEvent.DOWN, binding.tvScorePlayerOne, firstCounter) {
                firstCounter--
            }
        }

        // Player 2
        binding.bSecondUpScore.setOnClickListener {
            changeScore(ScoreEvent.UP, binding.tvScorePlayerTwo, secondCounter) {
                secondCounter++
            }
        }

        binding.bSecondDownScore.setOnClickListener {
            changeScore(ScoreEvent.DOWN, binding.tvScorePlayerTwo, secondCounter) {
                secondCounter--
            }
        }
    }

    private fun changeScore(event: ScoreEvent, textView: TextView, counter: Int, callback: (Int) -> Unit) {
        val upScore = when(event) {
            ScoreEvent.UP -> counter + 1
            ScoreEvent.DOWN -> counter - 1
        }
        if (upScore in DEFAULT_SCORE..FINAL_SCORE) {
            textView.text = upScore.toString()
            callback.invoke(upScore)
        }
        if (counter == FINAL_SCORE){
            // TODO: "Перехід на FinishGameFragment"
        }
    }

    companion object {
        private const val DEFAULT_SCORE = 0
        private const val FINAL_SCORE = 11
    }
}