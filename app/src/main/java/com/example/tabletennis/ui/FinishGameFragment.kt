package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.data.entites.ScoreDbEntity
import com.example.tabletennis.databinding.FragmentFinishGameBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FinishGameFragment : BaseFragment() {

    private lateinit var binding: FragmentFinishGameBinding
    private val args: FinishGameFragmentArgs by navArgs()
    private val dbViewModel: DatabaseViewModel by viewModels()

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
        with(args.gameDetail.winner) {
            setWinnerName(this)
        }
        initData()
        startNewGame()
        transitionToResultGameFragment()
        initOnBackPressCallback()
    }

    private fun initData() {
        binding.tvPlayerNameOne.text = args.gameDetail.firstPlayer.pName
        binding.tvPlayerNameTwo.text = args.gameDetail.secondPlayer.pName
        binding.tvPlayerScoreOne.text = args.gameDetail.firstPlayer.score.toString()
        binding.tvPlayerScoreTwo.text = args.gameDetail.secondPlayer.score.toString()
    }

    private fun setWinnerName(winner: PlayerNumber?) {
        winner?.let {
            val name = when(it) {
                PlayerNumber.FIRST -> args.gameDetail.firstPlayer.pName
                PlayerNumber.SECOND -> args.gameDetail.secondPlayer.pName
            }
            binding.tvWinnerName.text = name
        }
    }

    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
    }

    private fun startNewGame(){
        binding.bStartNewGame.setOnClickListener{
            val action = FinishGameFragmentDirections.actionFinishGameFragmentToStartGameFragment()
            findNavController().navigate(action)
        }
    }
    
    private fun transitionToResultGameFragment() {
        binding.bSaveResult.setOnClickListener {
            val action = FinishGameFragmentDirections
                .actionFinishGameFragmentToResultGameFragment()
            findNavController().navigate(action)
        }
    }
}