package com.seroean.apps.ui.budaya

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.BudayaData
import com.seroean.apps.data.response.BudayaDetailData

class BudayaViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val budayaStatus: LiveData<String> = mainRepository.message

    val isLoadingBudaya: LiveData<Boolean> = mainRepository.isLoading

    val budaya: LiveData<List<BudayaData>> = mainRepository.budaya

    val detailBudaya: LiveData<BudayaDetailData> = mainRepository.detailBudaya

    var isErrorBudaya: Boolean = false

    init {
        budayaStatus.observeForever { status ->
            isErrorBudaya = status != ""
        }
    }

    fun getBudaya(token: String) {
        mainRepository.getBudaya(token)
    }

    fun getDetailBudaya(token: String, budayaId: String) {
        mainRepository.getDetailBudaya(token, budayaId)
    }
}
