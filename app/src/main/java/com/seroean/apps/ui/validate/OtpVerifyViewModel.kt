package com.seroean.apps.ui.validate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository

class OtpVerifyViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _verifyOtpStatus = SingleLiveEvent<String>()
    val verifyOtpStatus: LiveData<String> get() = _verifyOtpStatus

    val isLoadingVerifyOtp: LiveData<Boolean> = mainRepository.isLoadingVerifyOtp

    private val _isErrorVerifyOtp = MutableLiveData<Boolean>()
    val isErrorVerifyOtp: LiveData<Boolean> get() = _isErrorVerifyOtp

    private val verifyObserver = Observer<String> { status ->
        _isErrorVerifyOtp.postValue(status != "Verifikasi OTP Sukses! Nikmati Fitur Kami Sekarang.")
        _verifyOtpStatus.postValue(status)
    }

    init {
        mainRepository.verifyOtpStatus.observeForever(verifyObserver)
    }

    fun verifyOtp(email: String, otp: String) {
        mainRepository.verifyOtp(email, otp)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.verifyOtpStatus.removeObserver(verifyObserver)
    }
}
