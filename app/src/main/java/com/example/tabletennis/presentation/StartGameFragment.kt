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

    private val defaultNameOne by lazy { getString(R.string.et_player_name_1) }
    private val defaultNameTwo by lazy { getString(R.string.et_player_name_2) }

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

    override fun onResume() {
        super.onResume()
        initNames()
    }

    //Initialization names
    private fun startGame() {
        binding.bStartGame.setOnClickListener {
            val gamerOne = binding.etFirstPlayer.text.defIfEmptyOrNull(defaultNameOne)
            val gamerTwo = binding.etSecondPlayer.text.defIfEmptyOrNull(defaultNameTwo)

            //Calling the SharedPreferences saving method
            savingNamesInSharedPref(gamerOne, gamerTwo)
            val action = StartGameFragmentDirections.actionStartGameFragmentToMainGameFragment(
                gamerOne,
                gamerTwo
            )
            findNavController().navigate(action)
        }
    }

    private fun initNames() {
        binding.etFirstPlayer
            .setText(Preferences.load(PREF_GAMER_ONE_KEY, defaultNameOne))
        binding.etSecondPlayer
            .setText(Preferences.load(PREF_GAMER_TWO_KEY, defaultNameTwo))
    }

    //SharedPreferences for remembering names
    private fun savingNamesInSharedPref(gamerOne: String, gamerTwo: String) {
        Preferences.save(PREF_GAMER_ONE_KEY, gamerOne)
        Preferences.save(PREF_GAMER_TWO_KEY, gamerTwo)
    }

    companion object {
        private const val PREF_GAMER_ONE_KEY = "gamer_one"
        private const val PREF_GAMER_TWO_KEY = "gamer_two"
    }
}