package com.solutionplus.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("nationality") val nationality: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("code") val code: String,
    @SerializedName("phone_code") val phoneCode: String,
    @SerializedName("visible") val visible: Boolean,
    @SerializedName("flag") val flag: String
)
