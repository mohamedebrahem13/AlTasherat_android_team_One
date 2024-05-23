package com.solutionplus.altasherat.features.personal_info.data.models.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("number")
    val number: String?,
    @SerializedName("country_code")
    val countryCode: String?
)
