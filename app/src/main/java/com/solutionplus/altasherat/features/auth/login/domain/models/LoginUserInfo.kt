package com.solutionplus.altasherat.features.auth.login.domain.models

data class LoginUserInfo(
    val token: String?,
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val countryCode: String,
    val number: String,
    val message: String?
)
