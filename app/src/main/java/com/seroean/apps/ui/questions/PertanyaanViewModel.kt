package com.seroean.apps.ui.pertanyaan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.PertanyaanData
import com.seroean.apps.data.response.PertanyaanDetailData

class PertanyaanViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val pertanyaanStatus: LiveData<String> = mainRepository.message
    val isLoadingPertanyaan: LiveData<Boolean> = mainRepository.isLoading
    val pertanyaan: LiveData<List<PertanyaanData>> = mainRepository.pertanyaan
    val detailPertanyaan: LiveData<PertanyaanDetailData> = mainRepository.detailPertanyaan

    var isErrorPertanyaan: Boolean = false

    init {
        pertanyaanStatus.observeForever { status ->
            isErrorPertanyaan = status != ""
        }
    }

    fun getPertanyaan(token: String) {
        mainRepository.getPertanyaan(token)
    }

    fun getDetailPertanyaan(token: String, pertanyaanId: String) {
        mainRepository.getDetailPertanyaan(token, pertanyaanId)
    }
}
