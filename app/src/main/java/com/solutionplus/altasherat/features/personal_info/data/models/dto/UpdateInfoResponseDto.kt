package com.solutionplus.altasherat.features.personal_info.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class UpdateInfoResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("user")
    val user: UserDto? = null
)