package com.solutionplus.altasherat.features.auth.login.data.models.request

import com.google.gson.annotations.SerializedName

data class PhoneLoginRequest(
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("number")
    val number: String
) {
    fun validatePhoneNumber(): Boolean {
        return !(number.any { !it.isDigit() } || number.isBlank() || number.length < 9 || number.length > 15)
    }
}
