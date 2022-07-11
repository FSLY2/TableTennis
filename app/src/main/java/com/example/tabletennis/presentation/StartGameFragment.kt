package com.example.tabletennis.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGame()
    }

    //Initialization of names and transition to the second fragment
    private fun startGame() {
        binding.bStartGame.setOnClickListener {
            var gamerOne: String = binding.etFirstPlayer.text.toString()
            var gamerTwo: String = binding.etSecondPlayer.text.toString()
            if (gamerOne.isEmpty()) {
                gamerOne = getString(R.string.et_player_name_1)
            }
            if (gamerTwo.isEmpty()) {
                gamerTwo = getString(R.string.et_player_name_2)
            }
            val action = StartGameFragmentDirections.actionStartGameFragmentToMainGameFragment(
                gamerOne,
                gamerTwo
            )
            findNavController().navigate(action)
        }
    }
}