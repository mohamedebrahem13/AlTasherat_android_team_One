package com.solutionplus.altasherat.features.auth.login.data.models.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val loginUserDto: LoginUserDto? = null
)