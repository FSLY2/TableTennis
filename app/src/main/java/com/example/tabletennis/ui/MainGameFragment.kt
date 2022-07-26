package com.example.tabletennis.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentMainGameBinding
import com.example.tabletennis.models.GameStatus
import com.example.tabletennis.models.Players
import com.example.tabletennis.models.ScoreEvent

class MainGameFragment : BaseFragment() {

    private lateinit var binding: FragmentMainGameBinding
    private val args: MainGameFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()
    
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

    //Method of assigning player names in MainViewModel
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

    //Method of assigning score in textview
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
            viewModel.changePlayerScore(viewModel.firstPlayer, ScoreEvent.UP)
        }
        binding.bFirstDownScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.firstPlayer, ScoreEvent.DOWN)
        }

        //Player 2
        binding.bSecondUpScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.secondPlayer, ScoreEvent.UP)
        }
        binding.bSecondDownScore.setOnClickListener {
            viewModel.changePlayerScore(viewModel.secondPlayer, ScoreEvent.DOWN)
        }
    }

    //Method of transition to FinishGameFragment
    private fun transitionToFinishFragment(winner: Players) {
        val action = MainGameFragmentDirections.actionMainGameFragmentToFinishGameFragment(
            viewModel.firstPlayer.name,
            viewModel.secondPlayer.name,
            viewModel.firstPlayer.score.toString(),
            viewModel.secondPlayer.score.toString(),
            winner.name
        )
        findNavController().navigate(action)
    }

    //AlertDialog creation method
    private fun createAlertDialog(): AlertDialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.alert_dialog_title))
            .setMessage(getString(R.string.alert_dialog_message))
            .setPositiveButton(getString(R.string.alert_dialog_button_yes)) { _, _ ->
                viewModel.cancel()
            }.setNegativeButton(getString(R.string.alert_dialog_button_no)) { dialog, _ ->
                dialog.cancel()
            }.create()

    //Init onBackPressCallback with AlertDialog
    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backDialog.show()
        }
    }
}