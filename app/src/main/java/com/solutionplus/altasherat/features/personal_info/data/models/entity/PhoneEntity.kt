package com.solutionplus.altasherat.features.personal_info.data.models.entity

import com.google.gson.annotations.SerializedName

internal data class PhoneEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("extension")
    val extension: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("holder_name")
    val holderName: String,
)