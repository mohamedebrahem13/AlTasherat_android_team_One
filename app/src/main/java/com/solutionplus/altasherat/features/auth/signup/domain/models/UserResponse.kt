package com.solutionplus.altasherat.features.auth.signup.domain.models

import com.solutionplus.altasherat.features.services.user.domain.models.User

data class UserResponse(
    val token: String,
    val user: User,
    val message: String
)
