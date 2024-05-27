package com.solutionplus.altasherat.features.services.user.domain.models

import java.time.LocalDate

data class User(
    val id: Int,
    val username: String,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: Phone,
    val image: Image,
    val birthDate: LocalDate,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val isBlocked: Int,
    val country: Country,
    val allPermissions: List<String>,
)