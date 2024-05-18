package com.solutionplus.altasherat.features.personal_info.domain.models

import java.time.LocalDateTime

data class User(
    val id: Int,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: Phone,
    val image: Image,
    val birthDate: LocalDateTime,
) {
    constructor() : this(
        id = 0, firstname = "", middlename = "", lastname = "", email = "",
        phone = Phone(), image = Image(), birthDate = LocalDateTime.now()
    )
}