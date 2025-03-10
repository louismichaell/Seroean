package com.seroean.apps.ui.notifikasi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.NotifikasiViewModelFactory
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.adapter.NotifikasiAdapter
import com.seroean.apps.data.response.NotifikasiData
import com.seroean.apps.databinding.ActivityNotifikasiBinding
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.dataStore
import java.util.concurrent.TimeUnit

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiBinding
    private lateinit var notifikasiAdapter: NotifikasiAdapter
    private lateinit var preferences: SettingsPreferences
    private lateinit var token: String

    private val notifikasiViewModel: NotifikasiViewModel by lazy {
        ViewModelProvider(
            this, NotifikasiViewModelFactory(this)
        )[NotifikasiViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SettingsPreferences.getInstance(dataStore)

        setupRecyclerView()
        requestNotificationPermission()

        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        authViewModel.getUserLoginToken().observe(this) { userToken ->
            token = userToken
            notifikasiViewModel.getNotifikasi(token)
            NotifikasiWorker.scheduleWorker(this, token)
        }

        notifikasiViewModel.notifikasi.observe(this) { notifikasiList ->
            if (notifikasiList.isNotEmpty()) {
                setNotifikasiData(notifikasiList)
            } else {
                showEmptyState()
            }
        }

        binding.backButtonNotifikasi.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        notifikasiAdapter = NotifikasiAdapter()
        binding.rvNotifikasi.apply {
            layoutManager = LinearLayoutManager(this@NotifikasiActivity)
            setHasFixedSize(true)
            adapter = notifikasiAdapter
        }
    }

    private fun setNotifikasiData(notifikasiList: List<NotifikasiData>) {
        if (::notifikasiAdapter.isInitialized) {
            notifikasiAdapter.setData(notifikasiList)
            binding.rvNotifikasi.visibility = View.VISIBLE
            binding.ivImage.visibility = View.GONE
            binding.tvTextNotifikasi.visibility = View.GONE
        }
    }

    private fun showEmptyState() {
        binding.rvNotifikasi.visibility = View.GONE
        binding.ivImage.visibility = View.VISIBLE
        binding.tvTextNotifikasi.visibility = View.VISIBLE
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }
    }

    companion object {
        private const val REQUEST_NOTIFICATION_PERMISSION = 1001
    }
}