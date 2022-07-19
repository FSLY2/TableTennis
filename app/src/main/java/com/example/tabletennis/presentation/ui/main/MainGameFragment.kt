package com.example.tabletennis.presentation.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.databinding.FragmentMainGameBinding
import com.example.tabletennis.presentation.common.ScoreEvent

class MainGameFragment : Fragment() {

    private lateinit var binding: FragmentMainGameBinding
    private val args: MainGameFragmentArgs by navArgs()

    private lateinit var gamerOne: String
    private lateinit var gamerTwo: String

    private lateinit var winner: String

    private var firstCounter: Int = DEFAULT_SCORE
    private var secondCounter: Int = DEFAULT_SCORE

    private val backDialog: AlertDialog by lazy {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Are you sure you want to go out?")
            .setPositiveButton("Yes") { _,_ ->
                findNavController().popBackStack()
            }.setNegativeButton("No") { dialog,_ ->
                dialog.cancel()
            }.create()
    }

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
        initOnBackPressCallback()
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

    //Implementation UP/DOWN score buttons for each player
    private fun gameScore() {
        //Player 1
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

        //Player 2
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
    
    private fun changeScore(
        event: ScoreEvent,
        textview: TextView,
        counter: Int,
        callback: () -> Unit
    ) {
        val newScore = when(event) {
            ScoreEvent.UP -> counter + 1
            ScoreEvent.DOWN -> counter - 1
        }
        if (newScore in DEFAULT_SCORE..FINAL_SCORE){
            textview.text = newScore.toString()
            callback.invoke()
        }
        //Check for a winner "Player 1"
        if (firstCounter == FINAL_SCORE){
            winner = gamerOne
            transitionToFinishFragment(winner)
        }
        //Check for a winner "Player 2"
        if (secondCounter == FINAL_SCORE){
            winner = gamerTwo
            transitionToFinishFragment(winner)
        }

    }

    //Method of transition to FinishGameFragment
    private fun transitionToFinishFragment(winner: String){
        val action =
            MainGameFragmentDirections.actionMainGameFragmentToFinishGameFragment(
                gamerOne,
                gamerTwo,
                firstCounter.toString(),
                secondCounter.toString(),
                winner
            )
        findNavController().navigate(action)
    }

    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backDialog.show()
        }
    }

    companion object {
        private const val DEFAULT_SCORE = 0
        private const val FINAL_SCORE = 11
    }
}