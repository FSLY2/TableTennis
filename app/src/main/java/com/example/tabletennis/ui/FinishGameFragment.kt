package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tabletennis.common.MyViewModelFactory
import com.example.tabletennis.databinding.FragmentFinishGameBinding
import com.example.tabletennis.data.ScoreDbEntity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FinishGameFragment : BaseFragment() {

    private lateinit var binding: FragmentFinishGameBinding
    private val args: FinishGameFragmentArgs by navArgs()
    private val dbViewModel: DatabaseViewModel by viewModels{
        MyViewModelFactory(requireContext())
    }

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
        saveResult()
        initOnBackPressCallback()
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
    
    private fun saveResult() {

        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val currentDate = formatter.format(now)

        val resultGameList = ScoreDbEntity(
            date = currentDate,
            pNameOne = gamerOne,
            pNameTwo = gamerTwo,
            winner = winner,
            pScoreOne = firstCounter,
            pScoreTwo = secondCounter
        )

        binding.bSaveResult.setOnClickListener {
            dbViewModel.insert(resultGameList)
            val action = FinishGameFragmentDirections
                .actionFinishGameFragmentToResultGameFragment()
            findNavController().navigate(action)
        }
    }
}