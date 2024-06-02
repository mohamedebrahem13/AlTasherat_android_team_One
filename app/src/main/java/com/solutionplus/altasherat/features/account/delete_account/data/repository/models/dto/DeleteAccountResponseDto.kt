package com.solutionplus.altasherat.features.account.delete_account.data.repository.models.dto

import com.google.gson.annotations.SerializedName

internal data class DeleteAccountResponseDto(
    @SerializedName("message")
    val message: String? = null,
)