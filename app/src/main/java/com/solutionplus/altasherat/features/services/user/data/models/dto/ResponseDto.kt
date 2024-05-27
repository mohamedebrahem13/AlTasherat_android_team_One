package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class ResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val userDto: UserDto? = null
)