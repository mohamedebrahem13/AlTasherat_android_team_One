package com.solutionplus.altasherat.features.services.user.data.models.entity

internal data class PhoneEntity(
    val id: Int,
    val countryCode: String,
    val number: String,
    val extension: String,
    val type: String,
    val holderName: String,
)