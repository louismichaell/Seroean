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
import com.seroean.apps.KulinerViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.data.response.KulinerDetailData
import com.seroean.apps.databinding.ActivityDetailKulinerBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.kuliner.KulinerViewModel
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.dataStore

class DetailKulinerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKulinerBinding
    private lateinit var kulinerId: String
    private var token: String? = null
    private lateinit var preferences: SettingsPreferences

    private val kulinerViewModel: KulinerViewModel by lazy {
        ViewModelProvider(
            this, KulinerViewModelFactory(this)
        )[KulinerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityDetailKulinerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)
        kulinerId = intent.getStringExtra(VARIABEL.EXTRA_KULINER_ID) ?: ""

        setupShimmerEffect(true)

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            if (!token.isNullOrEmpty()) {
                kulinerViewModel.getDetailKuliner(token!!, kulinerId)
            }
        }

        kulinerViewModel.detailKuliner.observe(this) { kuliner ->
            if (kuliner != null) {
                showDetailKuliner(kuliner)
                setupShimmerEffect(false)
            }
        }

        binding.apply {
            btnSharing.setOnClickListener {
                val shareText = """🍽 ${binding.tvNamakuliner.text}
📍 Lokasi: ${binding.tvAlamat.text}
☎ Telepon: ${binding.tvSubNoTelpTxt.text}
⭐ Rating: ${binding.tvRatingCount.text}
💳 Opsi: ${binding.tvSubopsi.text}
📸 Lihat foto: ${kulinerViewModel.detailKuliner.value?.foto}

#Kuliner #ExploreKuliner""".trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.sharing)))
            }

            backButtonKuliner.setOnClickListener {
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
    private fun showDetailKuliner(kuliner: KulinerDetailData) {
        binding.apply {
            tvNamakuliner.text = kuliner.nama
            tvRatingCount.text = kuliner.rating.toString()
            tvAlamat.text = kuliner.lokasi
            tvSubProvinsitxt.text = kuliner.provinsi
            tvSubNoTelpTxt.text = kuliner.telepon
            tvSubopsi.text = kuliner.opsi
            detailImageView.loadImage(kuliner.foto)
            imgsub1.loadImage(kuliner.image)
            imgsub2.loadImage(kuliner.image2)
            imgsub3.loadImage(kuliner.image3)
        }
    }
}
