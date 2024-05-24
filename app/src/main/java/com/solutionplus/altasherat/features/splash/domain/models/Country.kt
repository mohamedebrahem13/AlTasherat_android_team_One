package com.solutionplus.altasherat.features.splash.domain.models

data class Country(
    val id: Int,
    val name: String,
    val nationality: String,
    val currency: String,
    val code: String,
    val phoneCode: String,
    val visible: Boolean,
    val flag: String
)