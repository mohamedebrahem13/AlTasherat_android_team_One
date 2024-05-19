package com.solutionplus.altasherat.features.auth.signup.data.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("middlename")
    val middleName: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    @SerializedName("country")
    val countryId: String,
    @SerializedName("phone")
    val phoneRequest: PhoneRequest
)
