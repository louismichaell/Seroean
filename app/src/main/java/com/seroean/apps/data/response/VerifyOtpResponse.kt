package com.seroean.apps.data.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)