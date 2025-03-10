package com.seroean.apps.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seroean.apps.data.response.BudayaData
import com.seroean.apps.data.response.BudayaDetailData
import com.seroean.apps.data.response.BudayaResponse
import com.seroean.apps.data.response.CheckEmailResponse
import com.seroean.apps.data.response.DetailBudayaResponse
import com.seroean.apps.data.response.DetailKulinerResponse
import com.seroean.apps.data.response.DetailPertanyaanResponse
import com.seroean.apps.data.response.DetailSejarahResponse
import com.seroean.apps.data.response.EditProfileResponse
import com.seroean.apps.data.response.KulinerData
import com.seroean.apps.data.response.KulinerDetailData
import com.seroean.apps.data.response.KulinerResponse
import com.seroean.apps.data.response.LoginResponse
import com.seroean.apps.data.response.NotifikasiData
import com.seroean.apps.data.response.NotifikasiResponse
import com.seroean.apps.data.response.PertanyaanData
import com.seroean.apps.data.response.PertanyaanDetailData
import com.seroean.apps.data.response.PertanyaanResponse
import com.seroean.apps.data.response.WisataData
import com.seroean.apps.data.response.WisataResponse
import com.seroean.apps.data.response.RegisterResponse
import com.seroean.apps.data.response.RequestOtpResponse
import com.seroean.apps.data.response.ResetPasswordResponse
import com.seroean.apps.data.response.SejarahData
import com.seroean.apps.data.response.SejarahDetailData
import com.seroean.apps.data.response.SejarahResponse
import com.seroean.apps.data.response.User
import com.seroean.apps.data.response.UserResponse
import com.seroean.apps.data.response.VerifyOtpResponse
import com.seroean.apps.data.response.WisataDetailData
import com.seroean.apps.data.response.WisataDetailResponse
import com.seroean.apps.data.retrofit.ApiConfig
import com.seroean.apps.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeroeanRepository(private val apiService: ApiService) {

    private val _isLoadingRegister = MutableLiveData<Boolean>()
    val isLoadingRegister: LiveData<Boolean> = _isLoadingRegister
    private val _registerStatus = MutableLiveData<String>()
    val registerStatus: LiveData<String> = _registerStatus
    var isErrorRegister: Boolean = false

    private val _isLoadingReset = MutableLiveData<Boolean>()
    val isLoadingReset: LiveData<Boolean> = _isLoadingReset
    private val _resetStatus = MutableLiveData<String>()
    val resetStatus: LiveData<String> = _resetStatus
    var isErrorReset: Boolean = false

    private val _isLoadingOtp = MutableLiveData<Boolean>()
    private val _otpStatus = MutableLiveData<String>()
    val otpStatus: LiveData<String> = _otpStatus
    var isErrorOtp: Boolean = false

    private val _isLoadingVerifyOtp = MutableLiveData<Boolean>()
    val isLoadingVerifyOtp: LiveData<Boolean> = _isLoadingVerifyOtp
    private val _verifyOtpStatus = MutableLiveData<String>()
    val verifyOtpStatus: LiveData<String> = _verifyOtpStatus
    var isErrorVerifyOtp: Boolean = false

    private val _isLoadingCheckEmail = MutableLiveData<Boolean>()
    val isLoadingCheckEmail: LiveData<Boolean> = _isLoadingCheckEmail
    private val _checkEmailStatus = MutableLiveData<String>()
    val checkEmailStatus: LiveData<String> = _checkEmailStatus
    private val _emailExists = MutableLiveData<Boolean>()
    val emailExists: LiveData<Boolean> = _emailExists
    private val _isVerified = MutableLiveData<Boolean>()
    val isVerified: LiveData<Boolean> get() = _isVerified
//    var isErrorCheckEmail = false

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var isError: Boolean = false

    private val _biodata = MutableLiveData<User>()
    val biodata: LiveData<User> = _biodata

    private val _wisata = MutableLiveData<List<WisataData>>()
    val wisata: LiveData<List<WisataData>> = _wisata

    private val _kuliner = MutableLiveData<List<KulinerData>>()
    val kuliner: LiveData<List<KulinerData>> = _kuliner

    private val _budaya = MutableLiveData<List<BudayaData>>()
    val budaya: LiveData<List<BudayaData>> = _budaya

    private val _sejarah = MutableLiveData<List<SejarahData>>()
    val sejarah: LiveData<List<SejarahData>> = _sejarah

    private val _pertanyaan = MutableLiveData<List<PertanyaanData>>()
    val pertanyaan: LiveData<List<PertanyaanData>> = _pertanyaan

    private val _notifikasi = MutableLiveData<List<NotifikasiData>>()
    val notifikasi: LiveData<List<NotifikasiData>> = _notifikasi

    private val _detailWisata = MutableLiveData<WisataDetailData>()
    val detailWisata: LiveData<WisataDetailData> = _detailWisata

    private val _detailBudaya = MutableLiveData<BudayaDetailData>()
    val detailBudaya: LiveData<BudayaDetailData> = _detailBudaya

    private val _detailSejarah = MutableLiveData<SejarahDetailData>()
    val detailSejarah: LiveData<SejarahDetailData> = _detailSejarah

    private val _detailKuliner = MutableLiveData<KulinerDetailData>()
    val detailKuliner: LiveData<KulinerDetailData> = _detailKuliner

    private val _detailPertanyaan = MutableLiveData<PertanyaanDetailData>()
    val detailPertanyaan: LiveData<PertanyaanDetailData> = _detailPertanyaan

    private val _editBiodataStatus = MutableLiveData<String>()
    val editBiodataStatus: LiveData<String> = _editBiodataStatus
    private val _isLoadingeditBiodata = MutableLiveData<Boolean>()
    val isLoadingeditBiodata: LiveData<Boolean> = _isLoadingeditBiodata
    var isErroreditBiodata: Boolean = false

    private val _isLoadingLogin = MutableLiveData<Boolean>()
    val isLoadingLogin: LiveData<Boolean> = _isLoadingLogin
    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> = _loginStatus
    private val _login = MutableLiveData<LoginResponse>()
    val loginUser: LiveData<LoginResponse> = _login
    var isErrorLogin: Boolean = false

    fun register(name: String, email: String, location: String, password: String) {
        _isLoadingRegister.value = true
        val api = ApiConfig.getApiService().register(name, email, location, password)
        api.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoadingRegister.value = false
                if (response.isSuccessful) {
                    isErrorRegister = false
                    _registerStatus.value =
                        "Akun Berhasil Dibuat! Nikmati Semua Fitur Kami Sekarang."
                } else {
                    isErrorRegister = true
                    when (response.code()) {
                        400 -> _registerStatus.value =
                            "Email sudah digunakan, harap gunakan alamat email lain."

                        408 -> _registerStatus.value =
                            "Terjadi Kesalahan, Mohon Periksa Koneksi Internet Anda."

                        500 -> _registerStatus.value =
                            "Permintaan Tidak Dapat Diproses Saat Ini."

                        else -> {
                            _registerStatus.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                isErrorRegister = true
                _isLoadingRegister.value = false
                _registerStatus.value = t.message.toString()
            }

        })
    }

    fun login(email: String, password: String) {
        _isLoadingLogin.value = true
        val api = ApiConfig.getApiService().login(email, password)
        api.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val responseBody = response.body()
                _isLoadingLogin.value = false
                if (response.isSuccessful) {
                    isErrorLogin = false
                    _login.value = responseBody!!
                    _loginStatus.value =
                        "Selamat Datang ${_login.value!!.data.name} di Seroean"
                } else {
                    isErrorLogin = true
                    when (response.code()) {
                        400 -> _loginStatus.value =
                            "Kata sandi salah, silakan periksa kembali dan coba lagi."

                        401 -> _loginStatus.value = "Silahkan lakukan Verifikasi Kode OTP."
                        408 -> _loginStatus.value =
                            "Terjadi Kesalahan, Mohon Periksa Koneksi Internet Anda."

                        500 -> _loginStatus.value =
                            "Permintaan Tidak Dapat Diproses Saat Ini."

                        else -> {
                            _loginStatus.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isErrorLogin = true
                _isLoadingLogin.value = false
                _loginStatus.value = t.message.toString()
            }

        })
    }

    fun resetPassword(email: String) {
        _isLoadingReset.value = true
        val api = ApiConfig.getApiService().resetPassword(email)
        api.enqueue(object : Callback<ResetPasswordResponse> {
            override fun onResponse(
                call: Call<ResetPasswordResponse>,
                response: Response<ResetPasswordResponse>
            ) {
                _isLoadingReset.value = false
                if (response.isSuccessful) {
                    isErrorReset = false
                    _resetStatus.value =
                        "Token verifikasi reset kata sandi telah dikirim ke email Anda."
                } else {
                    isErrorReset = true
                    when (response.code()) {
                        404 -> _resetStatus.value =
                            "Email tidak ditemukan, Silahkan coba lagi."

                        408 -> _resetStatus.value =
                            "Terjadi Kesalahan, Mohon Periksa Koneksi Internet Anda."

                        500 -> _resetStatus.value =
                            "Permintaan Tidak Dapat Diproses Saat Ini."

                        else -> {
                            _resetStatus.value = response.message()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                isErrorReset = true
                _isLoadingReset.value = false
                _resetStatus.value = t.message.toString()
            }

        })
    }

    fun requestOtp(email: String) {
        _isLoadingOtp.value = true
        val api = ApiConfig.getApiService().requestOtp(email)

        api.enqueue(object : Callback<RequestOtpResponse> {
            override fun onResponse(
                call: Call<RequestOtpResponse>,
                response: Response<RequestOtpResponse>
            ) {
                _isLoadingOtp.value = false
                if (response.isSuccessful) {
                    isErrorOtp = false
                    _otpStatus.value =
                        "Periksa email Anda! Kode OTP telah dikirim dan siap digunakan."
                } else {
                    isErrorOtp = true
                    when (response.code()) {
                        404 -> _otpStatus.value = "Email tidak ditemukan, silakan coba lagi."
                        408 -> _otpStatus.value =
                            "Terjadi kesalahan, mohon periksa koneksi internet Anda."

                        500 -> _otpStatus.value = "Permintaan tidak dapat diproses saat ini."
                        else -> _otpStatus.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<RequestOtpResponse>, t: Throwable) {
                isErrorOtp = true
                _isLoadingOtp.value = false
                _otpStatus.value = t.message.toString()
            }
        })
    }


    fun verifyOtp(email: String, otp: String) {
        _isLoadingVerifyOtp.value = true
        val api = ApiConfig.getApiService().verifyOtp(email, otp)

        api.enqueue(object : Callback<VerifyOtpResponse> {
            override fun onResponse(
                call: Call<VerifyOtpResponse>,
                response: Response<VerifyOtpResponse>
            ) {
                _isLoadingVerifyOtp.value = false
                if (response.isSuccessful) {
                    isErrorVerifyOtp = false
                    _verifyOtpStatus.value = "Verifikasi OTP Sukses! Nikmati Fitur Kami Sekarang."
                } else {
                    isErrorVerifyOtp = true
                    when (response.code()) {
                        401 -> _verifyOtpStatus.value = "Kode OTP salah atau kadaluarsa."
                        408 -> _verifyOtpStatus.value =
                            "Terjadi kesalahan, mohon periksa koneksi internet Anda."

                        500 -> _verifyOtpStatus.value = "Permintaan tidak dapat diproses saat ini."
                        else -> _verifyOtpStatus.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
                isErrorVerifyOtp = true
                _isLoadingVerifyOtp.value = false
                _verifyOtpStatus.value = t.message.toString()
            }
        })
    }

    fun checkEmail(email: String) {
        val apiService = ApiConfig.getApiService().checkEmail(email)

        apiService.enqueue(object : Callback<CheckEmailResponse> {
            override fun onResponse(
                call: Call<CheckEmailResponse>,
                response: Response<CheckEmailResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    val exists = responseBody.exists
                    val verified = responseBody.isVerified
                    _emailExists.postValue(exists)
                    _isVerified.postValue(verified)

                } else {
                    _emailExists.postValue(false)
                    _isVerified.postValue(false)
                }
            }

            override fun onFailure(call: Call<CheckEmailResponse>, t: Throwable) {
                _emailExists.postValue(false)
                _isVerified.postValue(false)
            }
        })
    }

    fun getBiodata(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getBiodata("Bearer $token")
        api.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _biodata.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun editBiodata(
        token: String,
        image: MultipartBody.Part,
        name: RequestBody,
        location: RequestBody) {
        _isLoadingeditBiodata.value = true
        val service = ApiConfig.getApiService().editBiodata(
            "Bearer $token", image, name, location)
        service.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                _isLoadingeditBiodata.value = false
                if (response.isSuccessful) {
                    isErroreditBiodata = false
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _editBiodataStatus.value = "Data pengguna berhasil diperbarui."
                    }
                } else {
                    isErroreditBiodata = true
                    _editBiodataStatus.value = response.message()
                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                _isLoadingeditBiodata.value = false
                isErroreditBiodata = true
                _editBiodataStatus.value = t.message
            }
        })
    }

    fun getWisata(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getWisata("Bearer $token")
        api.enqueue(object : Callback<WisataResponse> {
            override fun onResponse(
                call: Call<WisataResponse>,
                response: Response<WisataResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _wisata.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<WisataResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getKuliner(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getKuliner("Bearer $token")
        api.enqueue(object : Callback<KulinerResponse> {
            override fun onResponse(
                call: Call<KulinerResponse>,
                response: Response<KulinerResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _kuliner.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<KulinerResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getBudaya(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getBudaya("Bearer $token")
        api.enqueue(object : Callback<BudayaResponse> {
            override fun onResponse(call: Call<BudayaResponse>, response: Response<BudayaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _budaya.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<BudayaResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getSejarah(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getSejarah("Bearer $token")
        api.enqueue(object : Callback<SejarahResponse> {
            override fun onResponse(call: Call<SejarahResponse>, response: Response<SejarahResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _sejarah.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<SejarahResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getPertanyaan(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getPertanyaan("Bearer $token")
        api.enqueue(object : Callback<PertanyaanResponse> {
            override fun onResponse(call: Call<PertanyaanResponse>, response: Response<PertanyaanResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _pertanyaan.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<PertanyaanResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getNotifikasi(token: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getNotifikasi("Bearer $token")
        api.enqueue(object : Callback<NotifikasiResponse> {
            override fun onResponse(call: Call<NotifikasiResponse>, response: Response<NotifikasiResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _notifikasi.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<NotifikasiResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getDetailWisata(token: String, wisataId: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getdetailWisata("Bearer $token", wisataId)
        api.enqueue(object : Callback<WisataDetailResponse> {
            override fun onResponse(call: Call<WisataDetailResponse>, response: Response<WisataDetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailWisata.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<WisataDetailResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getDetailBudaya(token: String, budayaId: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailBudaya("Bearer $token", budayaId)
        api.enqueue(object : Callback<DetailBudayaResponse> {
            override fun onResponse(call: Call<DetailBudayaResponse>, response: Response<DetailBudayaResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailBudaya.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailBudayaResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getDetailSejarah(token: String, sejarahId: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailSejarah("Bearer $token", sejarahId)
        api.enqueue(object : Callback<DetailSejarahResponse> {
            override fun onResponse(call: Call<DetailSejarahResponse>, response: Response<DetailSejarahResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailSejarah.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailSejarahResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getDetailKuliner(token: String, kulinerId: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getdetailKuliner("Bearer $token", kulinerId)
        api.enqueue(object : Callback<DetailKulinerResponse> {
            override fun onResponse(call: Call<DetailKulinerResponse>, response: Response<DetailKulinerResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailKuliner.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailKulinerResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

    fun getDetailPertanyaan(token: String, pertanyaanId: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getDetailPertanyaan("Bearer $token", pertanyaanId)
        api.enqueue(object : Callback<DetailPertanyaanResponse> {
            override fun onResponse(call: Call<DetailPertanyaanResponse>, response: Response<DetailPertanyaanResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailPertanyaan.value = responseBody.data
                    }
                    _message.value = responseBody?.message.toString()
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<DetailPertanyaanResponse>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }

}