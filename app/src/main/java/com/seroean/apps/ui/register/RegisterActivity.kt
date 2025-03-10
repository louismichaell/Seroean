package com.seroean.apps.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.CheckEmailViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.RegisterViewModelFactory
import com.seroean.apps.databinding.ActivityRegisterBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.LoginActivity
import com.seroean.apps.ui.validate.OtpActivity
import com.seroean.apps.ui.welcome.WelcomeActivity
import www.sanju.motiontoast.MotionToastStyle

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(this, RegisterViewModelFactory(this))[RegisterViewModel::class.java]
    }

    private val checkEmailViewModel: CheckEmailViewModel by lazy {
        ViewModelProvider(this, CheckEmailViewModelFactory(this))[CheckEmailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lightStatusBar(window)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val emailGoogle = intent.getStringExtra(VARIABEL.EMAILGOOGLE)
        val nameGoogle = intent.getStringExtra(VARIABEL.NAMEGOOGLE)
        val isGoogleSignIn = !emailGoogle.isNullOrEmpty() && !nameGoogle.isNullOrEmpty()

        binding.apply {
            if (isGoogleSignIn) {
                etEmail.setText(emailGoogle)
                etName.setText(nameGoogle)
                etEmail.isEnabled = false
                etName.isEnabled = false
            } else {
                etEmail.isEnabled = true
                etName.isEnabled = true
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = nameGoogle ?: binding.etName.text.toString().trim()
            val email = emailGoogle ?: binding.etEmail.text.toString().trim()
            val location = binding.etLokasi.text.toString().trim()
            val password = binding.etPass.text.toString().trim()
            val confirmPassword = binding.etConfirmPass.text.toString().trim()

            if (!validateInputs(name, email, location, password, confirmPassword)) {
                customToast(
                    getString(R.string.regisFailed),
                    getString(R.string.ERROR_EDITEXT_EMPTY),
                    MotionToastStyle.WARNING
                )
                return@setOnClickListener
            }
            checkEmail(email, name, location, password)
        }

        binding.tvLogintxt.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
        binding.backButtonRegister.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, WelcomeActivity::class.java))
        }
    }

    private fun setupObservers() {
        registerViewModel.isLoadingRegister.observe(this) {
            showLoading(it)
        }

        registerViewModel.registerStatus.observe(this) { event ->
            event?.let { registerStatus ->
                if (registerViewModel.isErrorRegister.value == true && registerStatus.isNotEmpty()) {
                    customToast(
                        getString(R.string.regisFailed), registerStatus, MotionToastStyle.ERROR
                    )
                } else if (registerViewModel.isErrorRegister.value == false && registerStatus.isNotEmpty()) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val email = binding.etEmail.text.toString().trim()
                        navigateToOtp(email)
                    }, DELAY_TIME)
                }
            }
        }
    }


    private fun checkEmail(
        email: String,
        name: String,
        location: String,
        password: String
    ) {
        showLoading(true)

        checkEmailViewModel.emailExists.observe(this) { exists ->
            showLoading(false)

            if (exists) {
                checkEmailViewModel.isVerified.observe(this) { verified ->
                    if (verified) {
                        navigateToLogin()
                    } else {
                        navigateToOtp(email)
                    }
                }
                return@observe
            }
            handleNewEmail(name, email, location, password)
        }
        checkEmailViewModel.checkEmail(email)
    }

    private fun handleNewEmail(
        name: String,
        email: String,
        location: String,
        password: String
    ) {
        registerViewModel.register(name, email, location, password)
    }

    private fun navigateToOtp(email: String) {
        val intent = Intent(this, OtpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(VARIABEL.EMAIL, email)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        customToast(
            getString(R.string.regisFailed), getString(R.string.lanjutkan), MotionToastStyle.ERROR
        )
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME)
    }

    private fun validateInputs(
        name: String,
        email: String,
        location: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.etName.error = getString(R.string.ERROR_NAME_EMPTY)
            isValid = false
        } else if (name.length > 25) {
            binding.etName.error = getString(R.string.ERROR_NAME_TOOLONG)
            isValid = false
        } else {
            binding.etName.error = null
        }

        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.ERROR_EMAIL_EMPTY)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = getString(R.string.ERROR_EMAIL_INVALID_FORMAT)
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        if (location.isEmpty()) {
            binding.etLokasi.error = getString(R.string.ERROR_NAME_EMPTY)
            isValid = false
        } else if (location.length > 100) {
            binding.etLokasi.error = getString(R.string.ERROR_NAME_TOOLONG)
            isValid = false
        } else {
            binding.etLokasi.error = null
        }

        if (password.isEmpty()) {
            binding.etPass.error = getString(R.string.ERROR_PASSWORD_EMPTY)
            isValid = false
        } else if (password.length < 8) {
            binding.etPass.error = getString(R.string.ERROR_PASSWORD_LENGTH)
            isValid = false
        } else {
            binding.etPass.error = null
        }

        if (confirmPassword != password) {
            binding.etConfirmPass.error = getString(R.string.ERROR_PASSWORD_MISMATCH)
            isValid = false
        } else {
            binding.etConfirmPass.error = null
        }

        return isValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            progressOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}
