package com.seroean.apps.ui.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.seroean.apps.SingleLiveEvent
import com.seroean.apps.data.local.repository.SeroeanRepository

class ResetViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val _resetStatus = SingleLiveEvent<String>()
    val resetStatus: LiveData<String> get() = _resetStatus

    val isLoadingReset: LiveData<Boolean> = mainRepository.isLoadingReset

    private val _isErrorReset = MutableLiveData<Boolean>()
    val isErrorReset: LiveData<Boolean> get() = _isErrorReset

    private val resetObserver = Observer<String> { status ->
        _isErrorReset.value = !status.contains("berhasil", ignoreCase = true) &&
                !status.contains("token verifikasi", ignoreCase = true)
        _resetStatus.value = status
    }

    init {
        mainRepository.resetStatus.observeForever(resetObserver)
    }

    fun reset(email: String) {
        mainRepository.resetPassword(email)
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.resetStatus.removeObserver(resetObserver)
    }
}
