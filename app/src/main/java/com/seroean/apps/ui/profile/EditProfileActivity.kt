package com.seroean.apps.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.ProfileViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.databinding.ActivityEditProfileBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.downloadImageAndSave
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String
    private var profileImageUri: Uri? = null
    private var currentProfilePhotoPath: String? = null

    private val viewModel by lazy {
        ViewModelProvider(
            this, ProfileViewModelFactory(applicationContext)
        )[ProfileViewModel::class.java]
    }

    private val profilePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                currentProfilePhotoPath?.let {
                    binding.ivProfilePicture.setImageURI(Uri.fromFile(File(it)))
                }
            } else {
                customToast(
                    getString(R.string.edit_profile),
                    getString(R.string.IMAGE_CAPTURE_FAILED),
                    MotionToastStyle.ERROR
                )
            }
        }

    private val profileGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                binding.ivProfilePicture.setImageURI(it)
                profileImageUri = it
                currentProfilePhotoPath = uriToFile(it).absolutePath
            } ?: run {
                customToast(
                    getString(R.string.edit_profile),
                    getString(R.string.IMAGE_SHOW_FAILED),
                    MotionToastStyle.ERROR
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupShimmerEffect(true)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]
        authViewModel.getUserLoginToken().observe(this) {
            token = it
            viewModel.getBiodata(token)
        }

        viewModel.biodata.observe(this) { biodata ->
            if (biodata != null) {
                lifecycleScope.launch {
                    try {
                        val profileImageFile = downloadImageAndSave(
                            applicationContext, biodata.profilePicture, "image"
                        )

                        profileImageFile?.let {
                            binding.ivProfilePicture.setImageURI(Uri.fromFile(it))
                            currentProfilePhotoPath = it.absolutePath
                        }

                        binding.apply {
                            tvUserName.text = biodata.name
                            tvUserEmail.text = biodata.email
                            etNameEdit.setText(biodata.name)
                            etLokasi.setText(biodata.location)
                            etEmailEdit.setText(biodata.email)
                        }

                        setupShimmerEffect(false)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        customToast(
                            getString(R.string.edit_profile),
                            getString(R.string.ERROR_LOAD_IMAGES),
                            MotionToastStyle.ERROR
                        )
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            btnSimpan.setOnClickListener {
                val name = etNameEdit.text.toString().trim()
                val location = etLokasi.text.toString().trim()
                val profileImagePath = currentProfilePhotoPath

                if (!validateInputs(name, location)) {
                    customToast(
                        getString(R.string.edit_profile),
                        getString(R.string.ERROR_EDITEXT_EMPTY),
                        MotionToastStyle.WARNING
                    )
                    return@setOnClickListener
                }

                if (profileImagePath != null) {
                    lifecycleScope.launch {
                        try {
                            val profileFile = File(profileImagePath)
                            val compressedProfileFile =
                                if (profileFile.length() > 1 * 1024 * 1024) {
                                    withContext(Dispatchers.IO) {
                                        Compressor.compress(applicationContext, profileFile)
                                    }
                                } else {
                                    profileFile
                                }

                            val imagePart = MultipartBody.Part.createFormData(
                                "image",
                                "${System.currentTimeMillis()}_${UUID.randomUUID()}_profile.jpg",
                                compressedProfileFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                            )
                            viewModel.editBiodata(
                                token,
                                imagePart,
                                name.toRequestBody("text/plain".toMediaType()),
                                location.toRequestBody("text/plain".toMediaType()),
                            )
                            customToast(
                                getString(R.string.edit_profile),
                                getString(R.string.BIODATA_UPLOAD_SUCCESS),
                                MotionToastStyle.SUCCESS
                            )
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, DELAY_TIME)
                        } catch (e: IOException) {
                            customToast(
                                getString(R.string.edit_profile),
                                getString(R.string.ERROR_COMPRESSING_DATA),
                                MotionToastStyle.ERROR
                            )
                        }
                    }
                } else {
                    customToast(
                        getString(R.string.edit_profile),
                        getString(R.string.ERROR_IMAGE_EMPTY),
                        MotionToastStyle.ERROR
                    )
                }
            }

            ivProfilePicture.setOnClickListener {
                showImageSourceDialog()
            }
            btnBatal.setOnClickListener {
                finish()
            }
            backButtonEdit.setOnClickListener {
                finish()
            }
        }
    }

    private fun validateInputs(
        name: String,
        location: String,
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.etNameEdit.error = getString(R.string.ERROR_NAME_EMPTY)
            isValid = false
        } else if (name.length > 25) {
            binding.etNameEdit.error = getString(R.string.ERROR_NAME_TOOLONG)
            isValid = false
        } else {
            binding.etNameEdit.error = null
        }

        if (location.isEmpty()) {
            binding.etLokasi.error = getString(R.string.ERROR_NAME_EMPTY)
            isValid = false
        } else if (location.length > 100) {
            binding.etLokasi.error = getString(R.string.ERROR_NAME_TOOLONG)
            isValid = false
        } else {
            binding.etLokasi.error = null
        }

        return isValid
    }

    private fun uriToFile(uri: Uri): File {
        val contentResolver = contentResolver
        val file = File.createTempFile("temp_image", ".jpg", cacheDir)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    }


    private fun showImageSourceDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            when (options[item]) {
                "Take Photo" -> {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION
                        )
                    } else {
                        dispatchTakePictureIntent()
                    }
                }

                "Choose from Gallery" -> {
                    profileGalleryLauncher.launch(PickVisualMediaRequest())
                }

                "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        photoFile?.also {
            val photoURI: Uri =
                FileProvider.getUriForFile(this, "com.seroean.apps.fileprovider", it)
            profilePhotoLauncher.launch(photoURI)
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("Profile_$timeStamp", ".jpg", storageDir).apply {
            currentProfilePhotoPath = absolutePath
        }
    }

    private fun setupShimmerEffect(isLoading: Boolean) {
        binding.apply {
            shimmerContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
            contentContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
            if (isLoading) shimmerContainer.startShimmer() else shimmerContainer.stopShimmer()
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 100
    }
}
