package com.solutionplus.altasherat.features.personal_info.data.models.entity

import com.google.gson.annotations.SerializedName

internal data class CountryEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("phone_code")
    val phoneCode: String,
    @SerializedName("flag")
    val flag: String
)