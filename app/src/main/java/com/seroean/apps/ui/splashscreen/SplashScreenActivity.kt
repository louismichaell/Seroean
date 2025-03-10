package com.seroean.apps.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.databinding.ActivitySplashScreenBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.ONBOARDING
import com.seroean.apps.ui.login.dataStore
import com.seroean.apps.ui.navigation.FragmentActivity
import com.seroean.apps.ui.onboarding.OnBoardingActivity
import com.seroean.apps.ui.welcome.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoapp.setImageResource(R.drawable.ic_logoapp)

        val prefen = SettingsPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(prefen))[AuthViewModel::class.java]

        authViewModel.getUserLoginSession().observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashScreenActivity, FragmentActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }, DELAY_TIME)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }, DELAY_TIME)
            }
        }
    }
}
