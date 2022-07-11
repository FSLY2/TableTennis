package com.example.tabletennis.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    //Ініціалізація імен та перехід на другий фрагмент
    private fun startGame() {
        binding.bStartGame.setOnClickListener {
            var gamerOne: String = binding.etFirstPlayer.text.toString()
            var gamerTwo: String = binding.etSecondPlayer.text.toString()
            if (gamerOne.isEmpty()) {
                gamerOne = DEFAULT_GAMER_ONE
            }
            if (gamerTwo.isEmpty()) {
                gamerTwo = DEFAULT_GAMER_TWO
            }
            val action = StartGameFragmentDirections.actionStartGameFragmentToMainGameFragment(
                gamerOne,
                gamerTwo
            )
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val DEFAULT_GAMER_ONE = "Player 1"
        private const val DEFAULT_GAMER_TWO = "Player 2"
    }
}