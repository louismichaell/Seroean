package com.seroean.apps.ui.notifikasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.NotifikasiData

class NotifikasiViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val notifikasiStatus: LiveData<String> = mainRepository.message
    val isLoadingNotifikasi: LiveData<Boolean> = mainRepository.isLoading
    val notifikasi: LiveData<List<NotifikasiData>> = mainRepository.notifikasi

    var isErrorNotifikasi: Boolean = false

    init {
        notifikasiStatus.observeForever { status ->
            isErrorNotifikasi = status != ""
        }
    }

    fun getNotifikasi(token: String) {
        mainRepository.getNotifikasi(token)
    }
}