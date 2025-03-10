package com.seroean.apps.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository

class RegisterViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _registerStatus = SingleLiveEvent<String>()
    val registerStatus: LiveData<String> get() = _registerStatus

    val isLoadingRegister: LiveData<Boolean> = mainRepository.isLoadingRegister

    private val _isErrorRegister = MutableLiveData<Boolean>()
    val isErrorRegister: LiveData<Boolean> get() = _isErrorRegister

    private val registerObserver = Observer<String> { status ->
        if (status.isNotEmpty()) {
            _isErrorRegister.postValue(status != "Akun Berhasil Dibuat! Nikmati Semua Fitur Kami Sekarang.")
            _registerStatus.postValue(status)
        }
    }

    init {
        mainRepository.registerStatus.observeForever(registerObserver)
    }

    fun register(name: String, email: String, location: String, password: String) {
        mainRepository.register(name, email, location, password)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.registerStatus.removeObserver(registerObserver)
    }
}