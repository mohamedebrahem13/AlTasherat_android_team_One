package com.solutionplus.altasherat.features.personal_info.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class CountryDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("phone_code")
    val phoneCode: String? = null,
    @SerializedName("flag")
    val flag: String? = null
)