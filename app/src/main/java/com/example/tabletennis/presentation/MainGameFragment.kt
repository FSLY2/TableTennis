package com.example.tabletennis.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        gameScore()
    }

    //Метод присвоєння імен гравців в textview
    private fun initNames() {
        gamerOne = args.gamerOne
        gamerTwo = args.gamerTwo
        binding.tvFirstPlayerName.text = gamerOne
        binding.tvSecondPlayerName.text = gamerTwo
    }

    //Реалізація кнопок рахунку UP/DOWN для обох гравців
    private fun gameScore() {
        // Гравець 1
        binding.bFirstUpScore.setOnClickListener {
            if (firstCounter < FINAL_SCORE) {
                firstCounter++
                binding.tvScorePlayerOne.text = firstCounter.toString()
            }
            if (firstCounter == FINAL_SCORE){
                // TODO: "Перехід на FinishGameFragment"
            }
        }

        binding.bFirstDownScore.setOnClickListener {
            firstCounter--
            binding.tvScorePlayerOne.text = firstCounter.toString()
        }

        // Гравець 2
        binding.bSecondUpScore.setOnClickListener {
            if (secondCounter <= FINAL_SCORE) {
                secondCounter++
                binding.tvScorePlayerTwo.text = secondCounter.toString()
            }
            if (secondCounter == FINAL_SCORE){
                // TODO: "Перехід на FinishGameFragment"
            }
        }

        binding.bSecondDownScore.setOnClickListener {
            secondCounter--
            binding.tvScorePlayerTwo.text = secondCounter.toString()
        }
    }

    companion object {
        private const val DEFAULT_SCORE = 0
        private const val FINAL_SCORE = 11
    }
}