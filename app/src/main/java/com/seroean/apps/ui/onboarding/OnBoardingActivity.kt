package com.seroean.apps.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seroean.apps.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
