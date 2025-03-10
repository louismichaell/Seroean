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
import com.seroean.apps.BudayaViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.data.response.BudayaDetailData
import com.seroean.apps.databinding.ActivityDetailBudayaBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.loadImage
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.budaya.BudayaViewModel

class DetailBudayaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBudayaBinding
    private lateinit var budayaId: String
    private var token: String? = null
    private lateinit var preferences: SettingsPreferences

    private val budayaViewModel: BudayaViewModel by lazy {
        ViewModelProvider(
            this, BudayaViewModelFactory(this)
        )[BudayaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityDetailBudayaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)
        budayaId = intent.getStringExtra(VARIABEL.EXTRA_BUDAYA_ID) ?: ""

        setupShimmerEffect(true)

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            if (!token.isNullOrEmpty()) {
                budayaViewModel.getDetailBudaya(token!!, budayaId)
            }
        }

        budayaViewModel.detailBudaya.observe(this) { budaya ->
            if (budaya != null) {
                showDetailBudaya(budaya)
                setupShimmerEffect(false)
            }
        }

        binding.apply {
            btnSharing.setOnClickListener {
                val shareText = """🌍 ${binding.tvNamaBudaya.text}
📝 Deskripsi: ${binding.tvSubDeskripsi.text}
📸 Lihat foto: ${budayaViewModel.detailBudaya.value?.foto}

#Budaya #ExploreIndonesia""".trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.sharing)))
            }

            backButtonBudaya.setOnClickListener {
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
    private fun showDetailBudaya(budaya: BudayaDetailData) {
        binding.apply {
            tvNamaBudaya.text = budaya.nama
            tvSubDeskripsi.text = budaya.deskripsi.replace("\\n\\n", "\n\n")
            detailImageView.loadImage(budaya.foto)
            imgsub1.loadImage(budaya.image)
            imgsub2.loadImage(budaya.image2)
            imgsub3.loadImage(budaya.image3)
        }
    }
}
