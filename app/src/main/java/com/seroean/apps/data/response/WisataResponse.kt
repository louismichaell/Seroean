package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class WisataResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<WisataData>,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class WisataData(
    @field:SerializedName("wisata_id")
    val wisataId: String,

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("lokasi")
    val lokasi: String,

    @field:SerializedName("provinsi")
    val provinsi: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("rating")
    val rating: String,

    @field:SerializedName("foto")
    val foto: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("image2")
    val image2: String,

    @field:SerializedName("image3")
    val image3: String,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
) : Parcelable
