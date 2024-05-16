package com.solutionplus.altasherat.features.auth.signup.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: User? = null
)