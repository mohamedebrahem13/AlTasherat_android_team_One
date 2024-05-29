package com.solutionplus.altasherat.features.services.user.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class PhoneDto(
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("extension")
    val extension: String? = null,
    @SerializedName("holder_name")
    val holderName: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: String? = null,
    @SerializedName("type")
    val type: String? = null
)