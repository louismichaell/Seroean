package com.seroean.apps.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seroean.apps.databinding.ActivityWelcomeBinding
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.LoginActivity
import com.seroean.apps.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lightStatusBar(window)
        binding.apply {
            Daftar.setOnClickListener {
                val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            Masuk.setOnClickListener {
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}