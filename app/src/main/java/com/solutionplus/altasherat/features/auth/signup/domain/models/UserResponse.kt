package com.solutionplus.altasherat.features.auth.signup.domain.models

data class UserResponse(
    val token: String,
    val user: UserInfo,
    val message: String
)
