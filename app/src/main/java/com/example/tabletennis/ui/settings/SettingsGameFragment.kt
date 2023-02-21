package com.example.tabletennis.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tabletennis.databinding.FragmentSettingsGameBinding
import com.example.tabletennis.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsGameFragment: BaseFragment() {

    private lateinit var binding: FragmentSettingsGameBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitch()
        onSwitchClickListener()
        closeWindow()
        initShareButton()
        initOnBackPressCallback()
    }

    //Switch part 11 or 21
    private fun onSwitchClickListener() {
        binding.switchPart.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.finalScore = FINAL_SCORE_LONG
            } else {
                viewModel.finalScore = FINAL_SCORE_DEF
            }
        }
    }

    //Check switch position TRUE/FALSE
    private fun initSwitch() {
        binding.switchPart.isChecked =
            viewModel.finalScore == FINAL_SCORE_LONG
    }

    //Close window
    private fun closeWindow() {
        binding.bCloseWin.setOnClickListener {
            val action = SettingsGameFragmentDirections.actionSettingsGameFragmentToStartGameFragment()
            findNavController().navigate(action)
        }
    }

    //Share button
    private fun initShareButton() {
        binding.bShare.setOnClickListener {
            val text = "You can download this app the following link"
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "https://google.com/")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    companion object {
        private const val FINAL_SCORE_LONG = 21
        private const val FINAL_SCORE_DEF = 11
    }
}