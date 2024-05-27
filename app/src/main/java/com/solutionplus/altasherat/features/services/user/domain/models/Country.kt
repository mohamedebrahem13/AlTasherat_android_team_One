package com.solutionplus.altasherat.features.services.user.domain.models

data class Country(
    val id: Int,
    val name: String,
    val currency: String,
    val code: String,
    val phoneCode: String,
    val flag: String,
    val isSelected: Boolean
)