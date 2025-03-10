package com.seroean.apps.data.retrofit

import com.seroean.apps.data.response.BudayaResponse
import com.seroean.apps.data.response.CheckEmailResponse
import com.seroean.apps.data.response.DetailBudayaResponse
import com.seroean.apps.data.response.DetailKulinerResponse
import com.seroean.apps.data.response.DetailPertanyaanResponse
import com.seroean.apps.data.response.DetailSejarahResponse
import com.seroean.apps.data.response.EditProfileResponse
import com.seroean.apps.data.response.KulinerResponse
import com.seroean.apps.data.response.LoginResponse
import com.seroean.apps.data.response.NotifikasiResponse
import com.seroean.apps.data.response.PertanyaanResponse
import com.seroean.apps.data.response.RegisterResponse
import com.seroean.apps.data.response.RequestOtpResponse
import com.seroean.apps.data.response.ResetPasswordResponse
import com.seroean.apps.data.response.SejarahResponse
import com.seroean.apps.data.response.UserResponse
import com.seroean.apps.data.response.VerifyOtpResponse
import com.seroean.apps.data.response.WisataDetailResponse
import com.seroean.apps.data.response.WisataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    @FormUrlEncoded
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("location") location: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("auth/request-reset-password")
    @FormUrlEncoded
    fun resetPassword(
        @Field("email") email: String,
    ): Call<ResetPasswordResponse>

    @FormUrlEncoded
    @POST("check-email")
    fun checkEmail(
        @Field("email") email: String
    ): Call<CheckEmailResponse>

    @POST("otp/request")
    @FormUrlEncoded
    fun requestOtp(
        @Field("email") email: String,
    ): Call<RequestOtpResponse>

    @POST("otp/verify")
    @FormUrlEncoded
    fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String,
    ): Call<VerifyOtpResponse>

    @GET("biodata")
    fun getBiodata(
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    @Multipart
    @POST("biodata/update")
    fun editBiodata(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("location") location: RequestBody,
    ): Call<EditProfileResponse>

    @GET("wisata")
    fun getWisata(
        @Header("Authorization") token: String
    ): Call<WisataResponse>

    @GET("kuliner")
    fun getKuliner(
        @Header("Authorization") token: String
    ): Call<KulinerResponse>

    @GET("budaya")
    fun getBudaya(
        @Header("Authorization") token: String
    ): Call<BudayaResponse>

    @GET("sejarah")
    fun getSejarah(
        @Header("Authorization") token: String
    ): Call<SejarahResponse>

    @GET("wisata/{wisataId}")
    fun getdetailWisata(
        @Header("Authorization") token: String,
        @Path("wisataId") wisataId: String
    ): Call<WisataDetailResponse>

    @GET("kuliner/{kulinerId}")
    fun getdetailKuliner(
        @Header("Authorization") token: String,
        @Path("kulinerId") kulinerId: String
    ): Call<DetailKulinerResponse>

    @GET("budaya/{budayaId}")
    fun getDetailBudaya(
        @Header("Authorization") token: String,
        @Path("budayaId") budayaId: String
    ): Call<DetailBudayaResponse>

    @GET("sejarah/{sejarahId}")
    fun getDetailSejarah(
        @Header("Authorization") token: String,
        @Path("sejarahId") sejarahId: String
    ): Call<DetailSejarahResponse>

    @GET("pertanyaan")
    fun getPertanyaan(
        @Header("Authorization") token: String
    ): Call<PertanyaanResponse>

    @GET("pertanyaan/{pertanyaanId}")
    fun getDetailPertanyaan(
        @Header("Authorization") token: String,
        @Path("pertanyaanId") pertanyaanId: String
    ): Call<DetailPertanyaanResponse>

    @GET("notifikasi")
    fun getNotifikasi(
        @Header("Authorization") token: String
    ): Call<NotifikasiResponse>


//    @GET("stories")
//    fun getStoryList(
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int? = 0,
//        @Header("Authorization") token: String,
//        ): Call<StoryResponse>
//
//    @Multipart
//    @POST("stories")
//    fun uploadStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: Float?,
//        @Part("lon") lon: Float?,
//    ): Call<StoryUploadResponse>
//
//    @GET("stories")
//    suspend fun getPagingStory(
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("location") location: Int? = 0,
//        @Header("Authorization") token: String,
//    ): PagingStoryResponse

}