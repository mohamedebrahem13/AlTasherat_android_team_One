package com.solutionplus.altasherat.features.auth.login.domain.models

import com.solutionplus.altasherat.features.services.user.domain.models.User

data class LoginUserResponse(
    val token: String,
    val user: User,
    val message: String
)
