package com.seroean.apps.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.data.response.WisataData

class MapsViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val wisataStatus: LiveData<String> = mainRepository.message
    val kulinerStatus: LiveData<String> = mainRepository.message

    val isLoadingWisata: LiveData<Boolean> = mainRepository.isLoading
    val isLoadingKuliner: LiveData<Boolean> = mainRepository.isLoading

    val wisata: LiveData<List<WisataData>> = mainRepository.wisata
    val kuliner: LiveData<List<KulinerData>> = mainRepository.kuliner

    var isErrorWisata: Boolean = false
    var isErrorKuliner: Boolean = false

    init {
        wisataStatus.observeForever { status ->
            isErrorWisata = status.isNotEmpty()
        }
        kulinerStatus.observeForever { status ->
            isErrorKuliner = status.isNotEmpty()
        }
    }

    fun getWisata(token: String) {
        mainRepository.getWisata(token)
    }

    fun getKuliner(token: String) {
        mainRepository.getKuliner(token)
    }
}
