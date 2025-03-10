package com.seroean.apps.ui.sejarah

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.SejarahData
import com.seroean.apps.data.response.SejarahDetailData

class SejarahViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val sejarahStatus: LiveData<String> = mainRepository.message
    val isLoadingSejarah: LiveData<Boolean> = mainRepository.isLoading
    val sejarah: LiveData<List<SejarahData>> = mainRepository.sejarah
    val detailSejarah: LiveData<SejarahDetailData> = mainRepository.detailSejarah

    private var isErrorSejarah: Boolean = false

    init {
        sejarahStatus.observeForever { status ->
            isErrorSejarah = status != ""
        }
    }

    fun getSejarah(token: String) {
        mainRepository.getSejarah(token)
    }

    fun getDetailSejarah(token: String, sejarahId: String) {
        mainRepository.getDetailSejarah(token, sejarahId)
    }

}
