package com.seroean.apps.ui.topwisata

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.WisataViewModelFactory
import com.seroean.apps.adapter.WisataTopAdapter
import com.seroean.apps.data.response.WisataData
import com.seroean.apps.databinding.ActivityTopWisataBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailWisataActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore

class TopWisataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopWisataBinding
    private lateinit var wisataAdapter: WisataTopAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val wisataViewModel: WisataViewModel by lazy {
        ViewModelProvider(
            this, WisataViewModelFactory(this)
        )[WisataViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityTopWisataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            wisataViewModel.getWisata(token)
        }

        wisataViewModel.wisata.observe(this) { wisataList ->
            if (wisataList.isNotEmpty()) {
                setWisataData(wisataList)
            }
        }

        binding.backbuttonTopWisata.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        wisataAdapter = WisataTopAdapter()
        binding.rvTopWisata.apply {
            layoutManager = LinearLayoutManager(this@TopWisataActivity)
            setHasFixedSize(true)
            adapter = wisataAdapter
        }

        wisataAdapter.setOnItemClickCallback(object : WisataTopAdapter.OnItemClickCallback {
            override fun onItemClicked(data: WisataData) {
                val intent = Intent(this@TopWisataActivity, DetailWisataActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_WISATA_ID, data.wisataId)
                startActivity(intent)
            }
        })
    }

    private fun setWisataData(wisataList: List<WisataData>) {
        if (::wisataAdapter.isInitialized) {
            val sortedList = wisataList.sortedByDescending { it.rating.toDoubleOrNull() ?: 0.0 }
            wisataAdapter.setData(sortedList)
            binding.rvTopWisata.visibility = android.view.View.VISIBLE
        }
    }
}
