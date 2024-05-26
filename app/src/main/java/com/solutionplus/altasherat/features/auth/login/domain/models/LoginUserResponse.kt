package com.solutionplus.altasherat.features.auth.login.domain.models

data class LoginUserResponse(
    val token: String,
    val user: LoginUserInfo,
    val message: String
)
