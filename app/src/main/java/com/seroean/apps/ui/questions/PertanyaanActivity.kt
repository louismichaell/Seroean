package com.seroean.apps.ui.questions

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.PertanyaanViewModelFactory
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.PertanyaanAdapter
import com.seroean.apps.data.response.PertanyaanData
import com.seroean.apps.databinding.ActivityPertanyaanBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailPertanyaanActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.pertanyaan.PertanyaanViewModel

class PertanyaanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPertanyaanBinding
    private lateinit var pertanyaanAdapter: PertanyaanAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val pertanyaanViewModel: PertanyaanViewModel by lazy {
        ViewModelProvider(
            this, PertanyaanViewModelFactory(this)
        )[PertanyaanViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityPertanyaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()
        setupSearch()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            pertanyaanViewModel.getPertanyaan(token)
        }

        pertanyaanViewModel.pertanyaan.observe(this) { pertanyaanList ->
            if (pertanyaanList.isNotEmpty()) {
                setPertanyaanData(pertanyaanList)
            }
        }

        binding.backButtonPertanyaan.setOnClickListener {
            finish()
        }
    }

    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterPertanyaan(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterPertanyaan(query: String) {
        val filteredPertanyaan = pertanyaanViewModel.pertanyaan.value?.filter {
            it.pertanyaan.contains(query, ignoreCase = true) ||
                    it.tipe.contains(query, ignoreCase = true)
        } ?: emptyList()

        pertanyaanAdapter.setData(filteredPertanyaan)
    }


    private fun setupRecyclerView() {
        pertanyaanAdapter = PertanyaanAdapter()
        binding.rvPertanyaan.apply {
            layoutManager = LinearLayoutManager(this@PertanyaanActivity)
            setHasFixedSize(true)
            adapter = pertanyaanAdapter
        }

        pertanyaanAdapter.setOnItemClickCallback(object : PertanyaanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PertanyaanData) {
                val intent = Intent(this@PertanyaanActivity, DetailPertanyaanActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_PERTANYAAN_ID, data.pertanyaanId)
                startActivity(intent)
            }
        })
    }

    private fun setPertanyaanData(pertanyaanList: List<PertanyaanData>) {
        if (::pertanyaanAdapter.isInitialized) {
            pertanyaanAdapter.setData(pertanyaanList)
            binding.rvPertanyaan.visibility = android.view.View.VISIBLE
        }
    }
}
