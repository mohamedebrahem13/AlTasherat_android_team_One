package com.solutionplus.altasherat.features.personal_info.data.models.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("number")
    val number: String,
    @SerializedName("country_code")
    val countryCode: String
) {
    fun isNumberValid(): Boolean {
        return Regex("^\\d{9,15}$").matches(number)
    }

    fun isCountryCodeValid(): Boolean {
        return Regex("^\\d{3,5}$").matches(countryCode)
    }
}
