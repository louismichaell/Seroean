package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class PemanduResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<PemanduData>,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class PemanduData(
    @field:SerializedName("pemandu_id")
    val pemanduId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("notelp")
    val notelp: String,

    @field:SerializedName("wisata_id")
    val wisataId: String,

    @field:SerializedName("profilePicture")
    val profilePicture: String
) : Parcelable
