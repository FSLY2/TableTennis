package com.example.tabletennis.ui.main

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
import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.common.invisible
import com.example.tabletennis.common.visible
import com.example.tabletennis.databinding.FragmentMainGameBinding
import com.example.tabletennis.models.GameStatus
import com.example.tabletennis.models.Players
import com.example.tabletennis.models.ScoreEvent
import com.example.tabletennis.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        initButtonListeners()
        initPlayers()
        initOnBackPressCallback()
    }

    private fun initGame() {
        initObservers()
        viewModel.initGameDetails()
    }

    //Method of assigning player names in MainViewModel
    private fun initPlayers() {
        binding.tvFirstPlayerName.text = args.gamerOne
        binding.tvSecondPlayerName.text = args.gamerTwo
        val gamerOne = args.gamerOne
        val gamerTwo = args.gamerTwo
        viewModel.setNameForGamer(gamerOne, gamerTwo)
    }

    private fun initObservers() {
        viewModel.gameStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is GameStatus.Init -> {
                    initPlayers()
                    with(status.gameDetail) {
                        setScoreByPlayer(firstPlayer)
                        setScoreByPlayer(secondPlayer)
                    }
                }
                is GameStatus.Resume -> {
                    with(status.gameDetail) {
                        setScoreByPlayer(firstPlayer)
                        setScoreByPlayer(secondPlayer)
                        feed?.let { changeFeedBallVisibility(it) }
                    }
                }
                //Saving results and transition to FinishGameFragment
                is GameStatus.Finish -> {
                    viewModel.saveGame(status.gameDetail)
                    val action =
                        MainGameFragmentDirections.actionMainGameFragmentToFinishGameFragment(
                            status.gameDetail
                        )
                    findNavController().navigate(action)
                }
                is GameStatus.Cancel -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    //Implementation UP/DOWN score buttons for each player
    private fun initButtonListeners() {
        //Player 1
        binding.bFirstUpScore.setOnClickListener {
            viewModel.changePlayerScore(PlayerNumber.FIRST, ScoreEvent.UP)
        }
        binding.bFirstDownScore.setOnClickListener {
            viewModel.changePlayerScore(PlayerNumber.FIRST, ScoreEvent.DOWN, false)
        }

        //Player 2
        binding.bSecondUpScore.setOnClickListener {
            viewModel.changePlayerScore(PlayerNumber.SECOND, ScoreEvent.UP)
        }
        binding.bSecondDownScore.setOnClickListener {
            viewModel.changePlayerScore(PlayerNumber.SECOND, ScoreEvent.DOWN, false)
        }
    }

    //Method of assigning score in textview
    private fun setScoreByPlayer(players: Players) {
        when (players) {
            is Players.First -> binding.tvScorePlayerOne
            is Players.Second -> binding.tvScorePlayerTwo
        }.text = players.score.toString()
    }

    //Method of assigning feed ball in imageview
    private fun changeFeedBallVisibility(feed: PlayerNumber) {
        when (feed) {
            PlayerNumber.FIRST -> {
                binding.ivFeedOne.visible()
                binding.ivFeedTwo.invisible()
            }
            PlayerNumber.SECOND -> {
                binding.ivFeedOne.invisible()
                binding.ivFeedTwo.visible()
            }
        }
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