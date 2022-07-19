package com.example.tabletennis.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.databinding.FragmentFinishGameBinding

class FinishGameFragment : Fragment() {

    private lateinit var binding: FragmentFinishGameBinding
    private val args: com.example.tabletennis.presentation.FinishGameFragmentArgs by navArgs()

    private lateinit var gamerOne: String
    private lateinit var gamerTwo: String
    private lateinit var winner: String

    private lateinit var firstCounter: String
    private lateinit var secondCounter: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        startNewGame()
    }

    private fun initData() {
        gamerOne = args.gamerOne
        gamerTwo = args.gamerTwo
        winner = args.winner
        firstCounter = args.firstCounter
        secondCounter = args.secondCounter
        binding.tvWinnerName.text = winner
        binding.tvPlayerNameOne.text = gamerOne
        binding.tvPlayerNameTwo.text = gamerTwo
        binding.tvPlayerScoreOne.text = firstCounter
        binding.tvPlayerScoreTwo.text = secondCounter
    }

    private fun startNewGame(){
        binding.bStartNewGame.setOnClickListener{
            val action =
                com.example.tabletennis.presentation.FinishGameFragmentDirections.actionFinishGameFragmentToStartGameFragment()
            findNavController().navigate(action)
        }
    }
}