package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class DetailPertanyaanResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: PertanyaanDetailData,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class PertanyaanDetailData(
    @field:SerializedName("pertanyaan_id")
    val pertanyaanId: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("pertanyaan")
    val pertanyaan: String,

    @field:SerializedName("jawaban")
    val jawaban: String,

    @field:SerializedName("datetime")
    val datetime: String
) : Parcelable
