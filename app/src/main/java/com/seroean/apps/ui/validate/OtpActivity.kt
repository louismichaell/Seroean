package com.seroean.apps.ui.validate

import android.content.Intent
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seroean.apps.OtpRequestViewModelFactory
import com.seroean.apps.OtpVerifyViewModelFactory
import com.seroean.apps.R
import com.seroean.apps.databinding.ActivityOtpBinding
import com.seroean.apps.ui.DELAY_TIME
import com.seroean.apps.ui.VARIABEL
import com.seroean.apps.ui.countdownDuration
import com.seroean.apps.ui.customToast
import com.seroean.apps.ui.lightStatusBar
import com.seroean.apps.ui.login.LoginActivity
import com.seroean.apps.ui.register.RegisterActivity
import www.sanju.motiontoast.MotionToastStyle

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding

    private val requestViewModel: OtpRequestViewModel by lazy {
        ViewModelProvider(this, OtpRequestViewModelFactory(this))[OtpRequestViewModel::class.java]
    }
    private val verifyViewModel: OtpVerifyViewModel by lazy {
        ViewModelProvider(this, OtpVerifyViewModelFactory(this))[OtpVerifyViewModel::class.java]
    }

    private lateinit var email: String
    private lateinit var otpInputs: List<EditText>
    private var countdownTimer: CountDownTimer? = null
    private var isCountdownRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        lightStatusBar(window)
        setContentView(binding.root)

        email = intent.getStringExtra(VARIABEL.EMAIL) ?: ""

        binding.textOtp.text = getString(R.string.OTP_TEXT, email)

        requestOtp()
        setupOtpInputs()
        setupObservers()
        setupListeners()
        initViews()
        clicKButton()
    }

    private fun setupOtpInputs() {
        otpInputs = listOf(
            binding.otpBox1,
            binding.otpBox2,
            binding.otpBox3,
            binding.otpBox4,
            binding.otpBox5,
            binding.otpBox6
        )

        for (i in otpInputs.indices) {
            otpInputs[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    if (text.length == 1) {
                        if (i < otpInputs.size - 1) otpInputs[i + 1].requestFocus()
                    } else if (text.isEmpty() && i > 0) {
                        otpInputs[i - 1].requestFocus()
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        requestViewModel.requestOtpStatus.observe(this) { status ->
            status?.let {}
        }

        verifyViewModel.isLoadingVerifyOtp.observe(this) {
            showLoading(it)
        }

        verifyViewModel.verifyOtpStatus.observe(this) { status ->
            status?.let {
                if (verifyViewModel.isErrorVerifyOtp.value == true) {
                    customToast(getString(R.string.FAILED_INFO_VERIFY_OTP), it, MotionToastStyle.ERROR)
                } else {
                    customToast(getString(R.string.SUCCESS_INFO_VERIFY_OTP), it, MotionToastStyle.SUCCESS)

                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@OtpActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }, DELAY_TIME)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            btnVerify.setOnClickListener {
                verifyOtp()
            }

            countdownTextView.setOnClickListener {
                if (!isCountdownRunning) {
                    requestOtp()
                    initViews()
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

    private fun requestOtp() {
        if (email.isEmpty()) {
            return
        }
        requestViewModel.requestOtp(email)
    }

    private fun verifyOtp() {
        val otpCode = otpInputs.joinToString("") { it.text.toString() }
        if (otpCode.length < 6) {
            customToast(getString(R.string.verifikasiotp), getString(R.string.ERROR_OTP_EMPTY), MotionToastStyle.WARNING)
            return
        }

        verifyViewModel.verifyOtp(email, otpCode)
    }

    private fun clicKButton(){
        binding.apply {
            backButtonValidasiotp.setOnClickListener{
                val intent = Intent(this@OtpActivity, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
    private fun initViews() {
        binding.countdownTextView.visibility = View.VISIBLE
        isCountdownRunning = true
        startCountdown()
    }

    private fun startCountdown() {
        countdownTimer?.cancel()
        countdownTimer = object : CountDownTimer(countdownDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.countdownTextView.text = getString(R.string.resend_in, secondsRemaining)
            }

            override fun onFinish() {
                binding.countdownTextView.text = getString(R.string.RESEND_OTP_NOW)
                binding.countdownTextView.visibility = View.VISIBLE
                isCountdownRunning = false
            }
        }.start()
    }
}
