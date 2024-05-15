package com.solutionplus.altasherat.features.splash.data.models.entity

data class CountryEntity(
    val id: Int,
    val name: String,
    val nationality: String,
    val currency: String,
    val code: String,
    val phoneCode: String,
    val visible: Boolean,
    val flag: String
)