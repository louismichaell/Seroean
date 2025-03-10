package com.seroean.apps.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.LoginResponse

class LoginViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _loginStatus = SingleLiveEvent<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    val isLoadingLogin: LiveData<Boolean> = mainRepository.isLoadingLogin
    val login: LiveData<LoginResponse> = mainRepository.loginUser

    private val _isErrorLogin = MutableLiveData<Boolean>()
    val isErrorLogin: LiveData<Boolean> get() = _isErrorLogin

    private val loginObserver = Observer<String> { status ->
        _isErrorLogin.postValue(status != "Selamat Datang ${login.value?.data?.name} di Seroean")
        _loginStatus.postValue(status)
    }

    init {
        mainRepository.loginStatus.observeForever(loginObserver)
    }

    fun login(email: String, password: String) {
        mainRepository.login(email, password)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.loginStatus.removeObserver(loginObserver)
    }
}
