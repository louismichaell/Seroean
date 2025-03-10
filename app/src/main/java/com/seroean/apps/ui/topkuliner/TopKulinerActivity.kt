package com.seroean.apps.ui.topkuliner

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.KulinerViewModelFactory
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.KulinerTopAdapter
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.databinding.ActivityTopKulinerBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailKulinerActivity
import com.seroean.apps.ui.kuliner.KulinerViewModel
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore

class TopKulinerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopKulinerBinding
    private lateinit var kulinerAdapter: KulinerTopAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val kulinerViewModel: KulinerViewModel by lazy {
        ViewModelProvider(
            this, KulinerViewModelFactory(this)
        )[KulinerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityTopKulinerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            kulinerViewModel.getKuliner(token)
        }

        kulinerViewModel.kuliner.observe(this) { kulinerList ->
            if (kulinerList.isNotEmpty()) {
                setKulinerData(kulinerList)
            }
        }

        binding.backbuttonTopKuliner.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        kulinerAdapter = KulinerTopAdapter()
        binding.rvTopKuliner.apply {
            layoutManager = LinearLayoutManager(this@TopKulinerActivity)
            setHasFixedSize(true)
            adapter = kulinerAdapter
        }

        kulinerAdapter.setOnItemClickCallback(object : KulinerTopAdapter.OnItemClickCallback {
            override fun onItemClicked(data: KulinerData) {
                val intent = Intent(this@TopKulinerActivity, DetailKulinerActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_KULINER_ID, data.kulinerId)
                startActivity(intent)
            }
        })
    }

    private fun setKulinerData(kulinerList: List<KulinerData>) {
        if (::kulinerAdapter.isInitialized) {
            val sortedList = kulinerList.sortedByDescending { it.rating }
            kulinerAdapter.setData(sortedList)
            binding.rvTopKuliner.visibility = android.view.View.VISIBLE
        }
    }

}
