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
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.WisataViewModelFactory
import com.seroean.apps.data.response.WisataDetailData
import com.seroean.apps.databinding.ActivityDetailWisataMainBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.topwisata.WisataViewModel

class DetailWisataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailWisataMainBinding
    private lateinit var wisataId: String
    private var token: String? = null
    private lateinit var preferences: SettingsPreferences

    private val wisataViewModel: WisataViewModel by lazy {
        ViewModelProvider(
            this, WisataViewModelFactory(this)
        )[WisataViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityDetailWisataMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)
        wisataId = intent.getStringExtra(VARIABEL.EXTRA_WISATA_ID) ?: ""

        setupShimmerEffect(true)

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            if (!token.isNullOrEmpty()) {
                wisataViewModel.getDetailWisata(token!!, wisataId)
            }
        }

        wisataViewModel.detailWisata.observe(this) { wisata ->
            if (wisata != null) {
                showDetailWisata(wisata)
                setupShimmerEffect(false)
            }
        }

        binding.apply {
            binding.btnSharing.setOnClickListener {
                val shareText = """🌍 ${binding.tvNamaWisata.text}
📍 Lokasi: ${binding.tvAlamat.text}
⭐ Rating: ${binding.tvRatingCount.text}
📝 Deskripsi: ${binding.tvSubDeskripsi.text}
📸 Lihat foto: ${wisataViewModel.detailWisata.value?.foto}

#Wisata #ExploreIndonesia""".trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.sharing)))
            }

            backButtonWisata.setOnClickListener {
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
    private fun showDetailWisata(wisata: WisataDetailData) {
        binding.apply {
            tvNamaWisata.text = wisata.nama
            tvRatingCount.text = wisata.rating
            tvAlamat.text = wisata.lokasi
            tvSubProvinsitxt.text = wisata.provinsi
            tvSubDeskripsi.text = wisata.deskripsi.replace("\\n\\n", "\n\n")
            detailImageView.loadImage(wisata.foto)
            imgsub1.loadImage(wisata.image)
            imgsub2.loadImage(wisata.image2)
            imgsub3.loadImage(wisata.image3)
        }
    }
}
