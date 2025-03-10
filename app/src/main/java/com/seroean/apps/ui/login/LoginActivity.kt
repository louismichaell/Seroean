package com.seroean.apps.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.seroean.apps.AuthModelFactory
import com.seroean.apps.AuthViewModel
import com.seroean.apps.BuildConfig
import com.seroean.apps.LoginViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.SettingsPreferences
import com.seroean.apps.databinding.ActivityLoginBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.forgot.ForgotPassActivity
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.SETTINGS_KEY
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.navigation.FragmentActivity
import com.seroean.apps.ui.register.RegisterActivity
import com.seroean.apps.ui.welcome.WelcomeActivity
import www.sanju.motiontoast.MotionToastStyle

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_KEY)

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val signIn = 1001
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(this))[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lightStatusBar(window)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        viewModel.isLoadingLogin.observe(this) {
            showLoading(it)
        }

        val preferences = SettingsPreferences.getInstance(dataStore)
        val authViewModel =
            ViewModelProvider(this, AuthModelFactory(preferences))[AuthViewModel::class.java]

        viewModel.loginStatus.observe(this) { loginStatus ->
            loginStatus?.let { status ->
                binding.apply {
                    if (viewModel.isErrorLogin.value == true && status.isNotEmpty()) {
                        customToast(getString(R.string.loginFailed), status, MotionToastStyle.ERROR)
                    } else if (viewModel.isErrorLogin.value == false && status.isNotEmpty()) {
                        viewModel.login.observe(this@LoginActivity) { authLogin ->
                            authLogin?.data?.let {
                                authViewModel.apply {
                                    saveUserLoginSession(true)
                                    saveUserLoginToken(it.token)
                                    saveUserLoginName(it.name)
                                    saveUserLoginUid(it.userId)
                                    saveUserLoginEmail(it.email)
                                }
                            }
                        }

                        customToast(
                            getString(R.string.loginSuccess),
                            status,
                            MotionToastStyle.SUCCESS
                        )

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@LoginActivity, FragmentActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }, DELAY_TIME)
                    }
                }
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.apply {
            backButtonLogin.setOnClickListener {
                val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            tvRegisterTxt.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            tvForgot.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPassActivity::class.java)
                startActivity(intent)
            }
            GoogleSignIn.setOnClickListener {
                showLoading(true)
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, signIn)
            }
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()

                val emailError = if (email.isEmpty()) {
                    getString(R.string.ERROR_EMAIL_EMPTY)
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    getString(R.string.ERROR_EMAIL_INVALID_FORMAT)
                } else null

                val passwordError = if (password.isEmpty()) {
                    getString(R.string.ERROR_PASSWORD_EMPTY)
                } else if (password.length < 8) {
                    getString(R.string.ERROR_PASSWORD_LENGTH)
                } else null

                binding.etEmail.error = emailError
                binding.etPass.error = passwordError

                val allErrors = listOf(emailError, passwordError)
                val anyErrors = allErrors.any { it != null }

                if (anyErrors) {
                    customToast(
                        getString(R.string.loginFailed),
                        getString(R.string.ERROR_EDITEXT_EMPTY),
                        MotionToastStyle.WARNING
                    )
                } else {
                    viewModel.login(email, password)
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == signIn) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        showLoading(false)
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            val email = account.email
            val name = account.displayName
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.putExtra(VARIABEL.EMAILGOOGLE, email)
            intent.putExtra(VARIABEL.NAMEGOOGLE, name)
            startActivity(intent)
        } catch (e: ApiException) {
            when (e.statusCode) {
                GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> {
                    customToast(getString(R.string.loginFailed), getString(R.string.errorGoogle), MotionToastStyle.ERROR)
                }
                GoogleSignInStatusCodes.SIGN_IN_FAILED -> {
                    customToast(getString(R.string.loginFailed), getString(R.string.failedGoogle), MotionToastStyle.ERROR)
                }
                else -> {
                    Log.d(getString(R.string.loginGoogle),"${e.statusCode} | ${e.localizedMessage}")
                }
            }
        }
    }
}
