package com.seroean.apps.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.response.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val mainRepository: SeroeanRepository) : ViewModel() {

    val profileStatus: LiveData<String> = mainRepository.message
    val detailStatus: LiveData<String> = mainRepository.message
    val editprofileStatus: LiveData<String> = mainRepository.editBiodataStatus

    val isLoadingProfile: LiveData<Boolean> = mainRepository.isLoading
    val isLoadingDetail: LiveData<Boolean> = mainRepository.isLoading
    val isLoadingeditBiodata: LiveData<Boolean> = mainRepository.isLoadingeditBiodata

    var isErrorProfile: Boolean = false
    var isErroreditBiodata: Boolean = false
    var isErrorDetail: Boolean = false

    init {
        profileStatus.observeForever { status ->
            isErrorProfile = status != ""
        }
    }

    val biodata:  LiveData<User> = mainRepository.biodata

//    val detailUser:  LiveData<UserDetail> = mainRepository.detailUser
//    val detailUserPost:  LiveData<List<PostsItem>> = mainRepository.detailUserPost

    fun getBiodata(token: String) {
        mainRepository.getBiodata(token)
    }

    fun editBiodata(
        token: String,
        image: MultipartBody.Part,
        name: RequestBody,
        location: RequestBody) {
        mainRepository.editBiodata(token, image, name, location)
    }

//    fun getDetailUser(token: String, email:String) {
//        mainRepository.getDetailUser(token,email)
//    }
}
