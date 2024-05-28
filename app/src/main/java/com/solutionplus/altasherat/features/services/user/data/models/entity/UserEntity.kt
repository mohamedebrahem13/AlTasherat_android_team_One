package com.solutionplus.altasherat.features.services.user.data.models.entity

internal data class UserEntity(
    val id: Int,
    val username: String,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: PhoneEntity,
    val image: ImageEntity,
    val birthDate: String,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val isBlocked: Int,
    val country: CountryEntity,
    val allPermissions: List<String>,
)