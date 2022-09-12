package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentStartGameBinding
import com.example.tabletennis.common.Preferences
import com.example.tabletennis.common.defIfEmptyOrNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartGameFragment : BaseFragment() {

    private lateinit var binding: FragmentStartGameBinding
    private val viewModel: MainViewModel by viewModels()

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
//            viewModel.setNameForGamer(gamerOne, gamerTwo)

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