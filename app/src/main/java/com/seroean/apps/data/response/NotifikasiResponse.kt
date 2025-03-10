package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class NotifikasiResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<NotifikasiData>,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class NotifikasiData(
    @field:SerializedName("notifikasi_id")
    val notifikasiId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("datetime")
    val datetime: String
) : Parcelable
