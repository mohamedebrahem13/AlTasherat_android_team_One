package com.solutionplus.altasherat.features.account.edit_password.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class EditPasswordResponseDto(
    @SerializedName("message")
    val message: String? = null,
)