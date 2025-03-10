package com.seroean.apps.data.response

import com.google.gson.annotations.SerializedName

data class RequestOtpResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)