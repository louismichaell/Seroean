package com.seroean.apps.ui.forgot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.R
import com.seroean.apps.ResetViewModelFactory
import com.seroean.apps.databinding.ActivityForgotPassBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.LoginActivity
import www.sanju.motiontoast.MotionToastStyle

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPassBinding
    private val viewModel: ResetViewModel by lazy {
        ViewModelProvider(this, ResetViewModelFactory(this))[ResetViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lightStatusBar(window)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            backButtonLupapass.setOnClickListener {
                val intent = Intent(this@ForgotPassActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            btnForgot.setOnClickListener {
                val email = etEmail.text.toString()

                val emailError = when {
                    email.isEmpty() -> getString(R.string.ERROR_EMAIL_EMPTY)
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() -> getString(R.string.ERROR_EMAIL_INVALID_FORMAT)

                    else -> null
                }

                etEmail.error = emailError

                if (emailError != null) {
                    customToast(
                        getString(R.string.FAILED_INFO_FORGOT_PASS),
                        getString(R.string.ERROR_EDITEXT_EMPTY),
                        MotionToastStyle.WARNING
                    )
                } else {
                    viewModel.reset(email)
                }
            }
        }

        viewModel.isLoadingReset.observe(this) {
            showLoading(it)
        }

        viewModel.resetStatus.observe(this) { resetStatus ->
            resetStatus?.let { status ->
                binding.apply {
                    if (viewModel.isErrorReset.value == true && status.isNotEmpty()) {
                        customToast(
                            getString(R.string.FAILED_INFO_FORGOT_PASS),
                            status,
                            MotionToastStyle.ERROR
                        )
                    } else if (viewModel.isErrorReset.value == false && status.isNotEmpty()) {
                        customToast(
                            getString(R.string.SUCCESS_INFO_FORGOT_PASS),
                            status,
                            MotionToastStyle.SUCCESS
                        )

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@ForgotPassActivity, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }, DELAY_TIME)
                    }
                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            progressOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
