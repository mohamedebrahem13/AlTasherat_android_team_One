package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class UserResponseDto(
    @SerializedName("user")
    val user: UserDto
)