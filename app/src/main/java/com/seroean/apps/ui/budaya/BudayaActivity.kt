package com.seroean.apps.ui.budaya

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.BudayaViewModelFactory
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.BudayaAdapter
import com.seroean.apps.data.response.BudayaData
import com.seroean.apps.databinding.ActivityBudayaBinding
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.detail.DetailBudayaActivity
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore

class BudayaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBudayaBinding
    private lateinit var budayaAdapter: BudayaAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val budayaViewModel: BudayaViewModel by lazy {
        ViewModelProvider(
            this, BudayaViewModelFactory(this)
        )[BudayaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityBudayaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()
        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            budayaViewModel.getBudaya(token)
        }

        budayaViewModel.budaya.observe(this) { budayaList ->
            if (budayaList.isNotEmpty()) {
                setBudayaData(budayaList)
            }
        }
        binding.backbuttonBudayaFull.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        budayaAdapter = BudayaAdapter()
        binding.rvBudayaFull.apply {
            layoutManager = GridLayoutManager(this@BudayaActivity, 2)
            setHasFixedSize(true)
            adapter = budayaAdapter
        }

        budayaAdapter.setOnItemClickCallback(object : BudayaAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BudayaData) {
                val intent = Intent(this@BudayaActivity, DetailBudayaActivity::class.java)
                intent.putExtra(VARIABEL.EXTRA_BUDAYA_ID, data.budayaId)
                startActivity(intent)
            }
        })
    }

    private fun setBudayaData(budayaList: List<BudayaData>) {
        if (::budayaAdapter.isInitialized) {
            budayaAdapter.setData(budayaList)
            binding.rvBudayaFull.visibility = android.view.View.VISIBLE
        }
    }
}
