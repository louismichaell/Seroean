package com.seroean.apps.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: UserData,

    @SerializedName("error")
    val error: Boolean
)

@Keep
data class UserData(
    @SerializedName("token")
    val token: String,

    @SerializedName("user_id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("createdAt")
    val createdAt: String
)
