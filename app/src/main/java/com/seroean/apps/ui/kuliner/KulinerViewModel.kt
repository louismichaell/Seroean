package com.seroean.apps.ui.kuliner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.data.response.KulinerDetailData
import com.seroean.apps.data.response.SejarahDetailData

class KulinerViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val kulinerStatus: LiveData<String> = mainRepository.message

    val isLoadingKuliner: LiveData<Boolean> = mainRepository.isLoading

    val kuliner: LiveData<List<KulinerData>> = mainRepository.kuliner

    val detailKuliner: LiveData<KulinerDetailData> = mainRepository.detailKuliner

    var isErrorKuliner: Boolean = false

    init {
        kulinerStatus.observeForever { status ->
            isErrorKuliner = status != ""
        }
    }

    fun getKuliner(token: String) {
        mainRepository.getKuliner(token)
    }

    fun getDetailKuliner(token: String, kulinerId:String) {
        mainRepository.getDetailKuliner(token,kulinerId)
    }
}
