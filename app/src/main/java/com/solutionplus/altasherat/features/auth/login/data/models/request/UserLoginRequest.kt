package com.solutionplus.altasherat.features.auth.login.data.models.request

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phone")
    val phoneLoginRequest: PhoneLoginRequest
)
