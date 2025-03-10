package com.seroean.apps.ui.about

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.seroean.apps.databinding.ActivityAboutBinding
import com.seroean.apps.ui.lightStatusBar

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lightStatusBar(window, false)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickButton()
    }

    private fun clickButton() {
        binding.backbuttonTentang.setOnClickListener {
           finish()
        }
    }
}
