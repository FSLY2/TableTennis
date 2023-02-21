package com.example.tabletennis.ui.sheets

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.common.put
import com.example.tabletennis.databinding.BottomSheetMenuLayoutBinding
import com.example.tabletennis.ui.DatabaseViewModel
import com.example.tabletennis.ui.main.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class BottomSheetMenu(private val callback: (Any) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMenuLayoutBinding
//    private val viewModel: MainViewModel by viewModels()

    //Request permission
    private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()) { }

    //Call camera
    private val getCameraImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as Bitmap
            var imageUri = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageInQ(bitmap)
                Log.d("TestSave", "Q URI: $imageUri")
                Log.d("TestSave", "Saved in Q success!")
            } else {
                imageUri = saveImageLegacyStyle(bitmap).toString()
                Log.d("TestSave", "Legacy Style URI: $imageUri")
                Log.d("TestSave", "Saved in Legacy Style success!")
            }
            callback.invoke(imageUri)
            dismiss()
        }
    }

    //Call gallery
    private val getGalleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            callback.invoke(it)
            dismiss()
        }
    }

    //Set theme for Bottom Sheet Dialog
    override fun getTheme() = R.style.ThemeOverlay_App_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetMenuLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        checkPermissions()
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

    //Check permission method
    private fun checkPermissions() {
        when {
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            )== PackageManager.PERMISSION_GRANTED) -> {  }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                )
            }
            else -> {
            }
        }
    }

    //Save image. Converting from Bitmap to Uri in Android 30+
    private fun saveImageInQ(bitmap: Bitmap): Uri? {
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        var fOutStream: OutputStream?
        var imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.getExternalStorageDirectory())
        }
        val contentResolver = requireActivity().contentResolver
        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, contentValues)
            fOutStream = imageUri?.let { resolver.openOutputStream(it) }
        }

        fOutStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

        contentValues.clear()
        imageUri?.let { contentResolver.update(it, contentValues, null, null) }

        return imageUri
    }

    //Save image. Converting from Bitmap to Uri in Android 26- (legacy style)
    private fun saveImageLegacyStyle(bitmap: Bitmap): Uri {
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val imagesDir = Environment.getExternalStorageDirectory()
        val image = File(imagesDir, fileName)
        val fOutStream = FileOutputStream(image)
        fOutStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        return Uri.fromFile(image)
    }

    //Initialization bottom sheet buttons
    private fun initListeners() {
        binding.apply {
            camera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getCameraImage.launch(intent)
            }
            gallery.setOnClickListener {
                getGalleryImage.launch("image/*")
            }
            link.setOnClickListener {
                val menuFragment = BottomSheetUrl { imageUrl ->
                    callback.invoke(imageUrl)
                    dismiss()
                }
                menuFragment.show(parentFragmentManager, "url_Fragment")
            }
        }
    }
}
