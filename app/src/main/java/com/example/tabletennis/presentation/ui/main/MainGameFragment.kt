package com.example.tabletennis.presentation.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.databinding.FragmentMainGameBinding
import com.example.tabletennis.presentation.common.GameStatus
import com.example.tabletennis.presentation.common.ScoreEvent
import com.example.tabletennis.presentation.modela.Players

class MainGameFragment : Fragment() {

    private lateinit var binding: FragmentMainGameBinding
    private val args: MainGameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()

    private val backDialog: AlertDialog by lazy { createAlertDialog() }

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
        initGame()
        initPlayers()
        initButtonListeners()
        initOnBackPressCallback()
    }

    private fun initPlayers() {
        with(args) {
            viewModel.initPlayers(gamerOne, gamerTwo)
        }
    }

    private fun initGame() {
        viewModel.gameStatus.observe(viewLifecycleOwner) { status ->
            when(status) {
                is GameStatus.Init -> {
                    with(status) {
                        binding.tvFirstPlayerName.text = firstPlayer.name
                        binding.tvSecondPlayerName.text = secondPlayer.name
                        setScoreByPlayer(firstPlayer)
                        setScoreByPlayer(secondPlayer)
                    }
                }
                is GameStatus.Resume -> {
                    setScoreByPlayer(status.player)
                }
                is GameStatus.Finish -> transitionToFinishFragment(status.winner)
                is GameStatus.Cancel -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setScoreByPlayer(players: Players) {
        when(players) {
            is Players.First -> binding.tvScorePlayerOne
            is Players.Second -> binding.tvScorePlayerTwo
        }.text = players.score.toString()
    }

    //Implementation UP/DOWN score buttons for each player
    private fun initButtonListeners() {
        //Player 1
        binding.bFirstUpScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.firstPlayer, ScoreEvent.UP) { player ->
                setScoreByPlayer(player)
            }
        }

        binding.bFirstDownScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.firstPlayer, ScoreEvent.DOWN) { player ->
                setScoreByPlayer(player)
            }
        }

        //Player 2
        binding.bSecondUpScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.secondPlayer, ScoreEvent.UP) { player ->
                setScoreByPlayer(player)
            }
        }

        binding.bSecondDownScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.secondPlayer, ScoreEvent.DOWN) { player ->
                setScoreByPlayer(player)
            }
        }
    }

    //Method of transition to FinishGameFragment
    private fun transitionToFinishFragment(winner: Players){
        val action =
            MainGameFragmentDirections.actionMainGameFragmentToFinishGameFragment(
                viewModel.firstPlayer.name,
                viewModel.secondPlayer.name,
                viewModel.firstPlayer.score.toString(),
                viewModel.secondPlayer.score.toString(),
                winner.name
            )
        findNavController().navigate(action)
    }

    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backDialog.show()
        }
    }

    private fun createAlertDialog(): AlertDialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Are you sure you want to go out?")
            .setPositiveButton("Yes") { _,_ ->
                viewModel.cancel()
            }.setNegativeButton("No") { dialog,_ ->
                dialog.cancel()
            }.create()
}