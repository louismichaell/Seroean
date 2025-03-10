package com.seroean.apps.ui.pemandu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.PemanduData

class PemanduViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    private val pemanduStatus: LiveData<String> = mainRepository.message
    val isLoadingPemandu: LiveData<Boolean> = mainRepository.isLoading
    val pemanduList: LiveData<List<PemanduData>> = mainRepository.pemanduList

    private var isErrorPemandu: Boolean = false

    init {
        pemanduStatus.observeForever { status ->
            isErrorPemandu = status != ""
        }
    }

    fun getPemanduByWisataId(token: String, wisataId: String) {
        mainRepository.getPemanduByWisataId(token, wisataId)
    }
}
