package com.seroean.apps.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.PertanyaanViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.data.response.PertanyaanDetailData
import com.seroean.apps.databinding.ActivityDetailPertanyaanBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.pertanyaan.PertanyaanViewModel

class DetailPertanyaanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPertanyaanBinding
    private lateinit var pertanyaanId: String
    private var token: String? = null
    private lateinit var preferences: SettingsPreferences

    private val pertanyaanViewModel: PertanyaanViewModel by lazy {
        ViewModelProvider(
            this, PertanyaanViewModelFactory(this)
        )[PertanyaanViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityDetailPertanyaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)
        pertanyaanId = intent.getStringExtra(VARIABEL.EXTRA_PERTANYAAN_ID) ?: ""

        setupShimmerEffect(true)

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            if (!token.isNullOrEmpty()) {
                pertanyaanViewModel.getDetailPertanyaan(token!!, pertanyaanId)
            }
        }

        pertanyaanViewModel.detailPertanyaan.observe(this) { pertanyaan ->
            if (pertanyaan != null) {
                showDetailPertanyaan(pertanyaan)
                setupShimmerEffect(false)
            }
        }

        binding.apply {
            backButtonPertanyaan.setOnClickListener {
                finish()
            }
            btnWa.setOnClickListener {
                val phoneNumber = getString(R.string.whatsapp_number)
                val message = getString(R.string.whatsapp_message) + " " + tvTitlePertanyaan.text

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
                }
                startActivity(intent)
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
    private fun showDetailPertanyaan(pertanyaan: PertanyaanDetailData) {
        binding.apply {
            tvTitlePertanyaan.text = pertanyaan.pertanyaan
            tvJawaban.text = pertanyaan.jawaban
            tvTipePertanyaan.text = pertanyaan.tipe
            tvDateTime.text = pertanyaan.datetime + " WIB"
        }
    }
}
