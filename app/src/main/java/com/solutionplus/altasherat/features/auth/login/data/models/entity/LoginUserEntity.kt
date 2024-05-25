package com.solutionplus.altasherat.features.auth.login.data.models.entity

data class LoginUserEntity(
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val countryCode: String,
    val number: String,
)
