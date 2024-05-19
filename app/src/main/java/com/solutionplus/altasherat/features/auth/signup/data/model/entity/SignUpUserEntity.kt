package com.solutionplus.altasherat.features.auth.signup.data.model.entity

data class SignUpUserEntity(
    val token: String,
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val countryCode: String,
    val number: String,
)
