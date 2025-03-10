package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class DetailSejarahResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: SejarahDetailData,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class SejarahDetailData(
    @field:SerializedName("sejarah_id")
    val sejarahId: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("foto")
    val foto: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("image2")
    val image2: String,

    @field:SerializedName("image3")
    val image3: String
) : Parcelable
