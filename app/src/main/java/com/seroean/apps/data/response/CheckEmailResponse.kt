package com.seroean.apps.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class CheckEmailResponse(
    @Keep
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("exists") val exists: Boolean,
    @SerializedName("isVerified") val isVerified: Boolean,
    @SerializedName("error") val error: Boolean
)

