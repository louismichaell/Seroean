package com.seroean.apps.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository

class CheckEmailViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _checkEmailStatus = SingleLiveEvent<String>()
    val checkEmailStatus: LiveData<String> get() = _checkEmailStatus

    private val _isErrorCheckEmail = MutableLiveData<Boolean>()
    val isErrorCheckEmail: LiveData<Boolean> get() = _isErrorCheckEmail

    private val _emailExists = MutableLiveData<Boolean>()
    val emailExists: LiveData<Boolean> get() = _emailExists

    private val _isVerified = MutableLiveData<Boolean>()
    val isVerified: LiveData<Boolean> get() = _isVerified

    private val checkEmailObserver = Observer<String> { status ->
        _isErrorCheckEmail.postValue(status.contains("email") || status.contains("belum terdaftar"))
        _checkEmailStatus.postValue(status)
    }

    private val emailExistsObserver = Observer<Boolean> { exists ->
        _emailExists.postValue(exists)
    }

    private val isVerifiedObserver = Observer<Boolean> { verified ->
        _isVerified.postValue(verified)
    }

    init {
        mainRepository.checkEmailStatus.observeForever(checkEmailObserver)
        mainRepository.emailExists.observeForever(emailExistsObserver)
        mainRepository.isVerified.observeForever(isVerifiedObserver)
    }

    fun checkEmail(email: String) {
        mainRepository.checkEmail(email)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.checkEmailStatus.removeObserver(checkEmailObserver)
        mainRepository.emailExists.removeObserver(emailExistsObserver)
        mainRepository.isVerified.removeObserver(isVerifiedObserver)
    }
}
