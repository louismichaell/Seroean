package com.seroean.apps.ui.validate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository

class OtpRequestViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _otpStatus = SingleLiveEvent<String>()
    val requestOtpStatus: LiveData<String> get() = _otpStatus

    private val _isErrorOtp = MutableLiveData<Boolean>()
    val isErrorRequestOtp: LiveData<Boolean> get() = _isErrorOtp

    private val requestObserver = Observer<String> { status ->
        _isErrorOtp.postValue(status != "Periksa email Anda! Kode OTP telah dikirim dan siap digunakan.")
        _otpStatus.postValue(status)
    }

    init {
        mainRepository.otpStatus.observeForever(requestObserver)
    }

    fun requestOtp(email: String) {
        mainRepository.requestOtp(email)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.otpStatus.removeObserver(requestObserver)
    }
}
