package com.seroean.apps.ui.topwisata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.WisataData
import com.seroean.apps.data.response.WisataDetailData

class WisataViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val wisataStatus: LiveData<String> = mainRepository.message

    val isLoadingWisata: LiveData<Boolean> = mainRepository.isLoading

    val wisata: LiveData<List<WisataData>> = mainRepository.wisata

    val detailWisata:  LiveData<WisataDetailData> = mainRepository.detailWisata

    var isErrorWisata: Boolean = false

    init {
        wisataStatus.observeForever { status ->
            isErrorWisata = status != ""
        }
    }

    fun getWisata(token: String) {
        mainRepository.getWisata(token)
    }

    fun getDetailWisata(token: String, wisataId:String) {
        mainRepository.getDetailWisata(token,wisataId)
    }
}
