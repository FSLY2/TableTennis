package com.example.tabletennis.ui.sheets

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SheetViewModel @Inject constructor() : ViewModel() {

//    fun callCamera() {
//        private val getCameraImage = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val bitmap = result.data?.extras?.get("data") as Bitmap
//                when (whereInsertImage) {
//                    1 -> Glide.with(this).load(bitmap).circleCrop().into(binding.ivFirstPlayer)
//                    2 -> Glide.with(this).load(bitmap).circleCrop().into(binding.ivSecondPlayer)
//                }
//            }
//        }
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        getCameraImage.launch(intent)
//    }

//    fun callGallery() {
//        val getGalleryImage =
//            registerForActivityResult(ActivityResultContracts.GetContent()) {
//                when (whereInsertImage) {
//                    1 -> Glide.with(this).load(it).circleCrop().into(binding.ivFirstPlayer)
//                    2 -> Glide.with(this).load(it).circleCrop().into(binding.ivSecondPlayer)
//                }
//            }
//        getGalleryImage.launch("image/*")
//    }

//    fun setImageFromUrl() {
//        val url = "https://cdn.iconscout.com/icon/free/png-256/kotlin-2752140-2284957.png"
//        val url = binding.etUrl.text.toString()
//        when (whereInsertImage) {
//            1 -> Glide.with(requireActivity()).load(url).circleCrop()
//                .into(binding.ivFirstPlayer)
//            2 -> Glide.with(requireActivity()).load(url).circleCrop()
//                .into(binding.ivSecondPlayer)
//        }
//        bottomSheetUrlDialog.hide()
//    }
}