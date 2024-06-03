package com.solutionplus.altasherat.features.account.personal_info.data.models.dto

import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal data class UpdateInfoResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("user")
    val user: UserDto? = null
)