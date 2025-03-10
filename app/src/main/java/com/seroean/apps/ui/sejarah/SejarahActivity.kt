package com.seroean.apps.ui.sejarah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.SejarahViewModelFactory
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.SejarahAdapter
import com.seroean.apps.data.response.SejarahData
import com.seroean.apps.databinding.ActivitySejarahBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailSejarahActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore

class SejarahActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySejarahBinding
    private lateinit var sejarahAdapter: SejarahAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val sejarahViewModel: SejarahViewModel by lazy {
        ViewModelProvider(
            this, SejarahViewModelFactory(this)
        )[SejarahViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivitySejarahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            sejarahViewModel.getSejarah(token)
        }

        sejarahViewModel.sejarah.observe(this) { sejarahList ->
            if (sejarahList.isNotEmpty()) {
                setSejarahData(sejarahList)
            }
        }

        binding.backbuttonSejarahFull.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        sejarahAdapter = SejarahAdapter()
        binding.rvSejarahFull.apply {
            layoutManager = GridLayoutManager(this@SejarahActivity, 2)
            setHasFixedSize(true)
            adapter = sejarahAdapter
        }

        sejarahAdapter.setOnItemClickCallback(object : SejarahAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SejarahData) {
                val intent = Intent(this@SejarahActivity, DetailSejarahActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_SEJARAH_ID, data.sejarahId)
                startActivity(intent)
            }
        })
    }

    private fun setSejarahData(sejarahList: List<SejarahData>) {
        if (::sejarahAdapter.isInitialized) {
            sejarahAdapter.setData(sejarahList)
            binding.rvSejarahFull.visibility = android.view.View.VISIBLE
        }
    }
}
