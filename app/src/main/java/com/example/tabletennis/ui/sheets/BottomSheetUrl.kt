package com.example.tabletennis.ui.sheets

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.tabletennis.R
import com.example.tabletennis.databinding.BottomSheetUrlLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetUrl(private val callback: (Any) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetUrlLayoutBinding
//    private val sheetViewModel: SheetViewModel by viewModels()

    //Set theme for Bottom Sheet Dialog
    override fun getTheme() = R.style.ThemeOverlay_App_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetUrlLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invokeListeners()
    }

    //Set transparent background for Bottom Sheet
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    //Expanded state for Bottom Sheet
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).apply {
                this.skipCollapsed = true
                this.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    //Initialization bottom sheet buttons
    private fun invokeListeners() {
        binding.apply {
            bPlaceUrlAdd.setOnClickListener {
                val url = etUrl.text.toString()
                callback.invoke(url)
                dismiss()
            }

            bPlaceUrlClose.setOnClickListener {
                dismiss()
            }
        }
    }
}