package com.solutionplus.altasherat.features.personal_info.domain.models

data class Phone(
    val id: Int,
    val countryCode: String,
    val number: String,
) {
    constructor() : this(id = 0, countryCode = "", number = "")
}