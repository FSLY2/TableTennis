package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentStartGameBinding
import com.example.tabletennis.common.Preferences
import com.example.tabletennis.common.defIfEmptyOrNull
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartGameFragment : BaseFragment() {

    private lateinit var binding: FragmentStartGameBinding

    //Initializing FloatingActionButtons
    private lateinit var fabOpenMenu: FloatingActionButton
    private lateinit var fabSettings: FloatingActionButton
    private lateinit var fabResultBoard: FloatingActionButton

    //Initializing animations for FloatingActionsButtons
    private val animFromBottom: Animation by lazy { AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fab_from_bottom
        )
    }
    private val animToBottom: Animation by lazy { AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fab_to_bottom
        )
    }
    private val animRotateOpen: Animation by lazy { AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open
        )
    }
    private val animRotateClose: Animation by lazy { AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close
        )
    }
    private var clicked: Boolean = false

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
        fabOpenMenu = binding.fabOpenMenu
        fabSettings = binding.fabSettings
        fabResultBoard = binding.fabResultBoard
        startGame()
        initMenuButtonListeners()
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

    ///FAB Animation///

    //Initializing listeners for FloatingActionsButtons
    private fun initMenuButtonListeners() {
        fabOpenMenu.setOnClickListener {
            onOpenButtonClicked()
        }
        fabSettings.setOnClickListener {
            val action = StartGameFragmentDirections.actionStartGameFragmentToSettingsGameFragment()
            findNavController().navigate(action)
        }
        fabResultBoard.setOnClickListener {
            val action = StartGameFragmentDirections.actionStartGameFragmentToResultGameFragment()
            findNavController().navigate(action)
        }
    }

    //Initializing animation for FloatingActionsButtons
    private fun onOpenButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    //Visibility for menu buttons
    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            fabSettings.visibility = View.VISIBLE
            fabResultBoard.visibility = View.VISIBLE
        } else {
            fabSettings.visibility = View.INVISIBLE
            fabResultBoard.visibility = View.INVISIBLE
        }
    }

    //Set animation for FloatingActionsButtons
    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            fabSettings.startAnimation(animFromBottom)
            fabResultBoard.startAnimation(animFromBottom)
            fabOpenMenu.startAnimation(animRotateOpen)
        } else {
            fabSettings.startAnimation(animToBottom)
            fabResultBoard.startAnimation(animToBottom)
            fabOpenMenu.startAnimation(animRotateClose)
        }
    }

    //Changing
    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            fabSettings.isClickable = true
            fabResultBoard.isClickable = true
        } else {
            fabSettings.isClickable = false
            fabResultBoard.isClickable = false
        }
    }

    // TODO: "Change FAB color"
    // TODO: "Remove one FAB button from XML"

    companion object {
        private const val PREF_GAMER_ONE_KEY = "gamer_one"
        private const val PREF_GAMER_TWO_KEY = "gamer_two"
    }
}