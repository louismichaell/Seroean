package com.seroean.apps.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
data class UserResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: User,

    @field:SerializedName("error")
    val error: Boolean
)

@Keep
@Parcelize
data class User(
    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("profilePicture")
    val profilePicture: String,

    @field:SerializedName("name")
    val name: String,


) : Parcelable