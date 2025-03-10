package com.seroean.apps.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.SejarahViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.data.response.SejarahDetailData
import com.seroean.apps.databinding.ActivityDetailSejarahBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.sejarah.SejarahViewModel

class DetailSejarahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSejarahBinding
    private lateinit var sejarahId: String
    private var token: String? = null
    private lateinit var preferences: SettingsPreferences

    private val sejarahViewModel: SejarahViewModel by lazy {
        ViewModelProvider(
            this, SejarahViewModelFactory(this)
        )[SejarahViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityDetailSejarahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)
        sejarahId = intent.getStringExtra(VARIABEL.EXTRA_SEJARAH_ID) ?: ""

        setupShimmerEffect(true)

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            if (!token.isNullOrEmpty()) {
                sejarahViewModel.getDetailSejarah(token!!, sejarahId)
            }
        }

        sejarahViewModel.detailSejarah.observe(this) { sejarah ->
            if (sejarah != null) {
                showDetailSejarah(sejarah)
                setupShimmerEffect(false)
            }
        }

        binding.apply {
            btnSharing.setOnClickListener {
                val shareText = """🏛️ ${binding.tvNamaSejarah.text}
📝 Deskripsi: ${binding.tvSubDeskripsi.text}
📸 Lihat foto: ${sejarahViewModel.detailSejarah.value?.foto}

#Sejarah #ExploreIndonesia""".trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.sharing)))
            }

            backButtonSejarah.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupShimmerEffect(isLoading: Boolean) {
        binding.apply {
            shimmerContainer.apply {
                visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) startShimmer() else stopShimmer()
            }
            contentContainer.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailSejarah(sejarah: SejarahDetailData) {
        binding.apply {
            tvNamaSejarah.text = sejarah.nama
            tvSubDeskripsi.text = sejarah.deskripsi.replace("\\n\\n", "\n\n")
            detailImageView.loadImage(sejarah.foto)
            imgsub1.loadImage(sejarah.image)
            imgsub2.loadImage(sejarah.image2)
            imgsub3.loadImage(sejarah.image3)
        }
    }
}
