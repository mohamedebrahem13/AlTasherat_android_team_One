package com.solutionplus.altasherat.features.auth.login.data.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phone")
    val phoneRequest: PhoneRequest
)
