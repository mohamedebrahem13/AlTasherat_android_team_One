package com.solutionplus.altasherat.features.personal_info.domain.models

import java.time.LocalDate

data class User(
    val id: Int,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: Phone,
    val image: Image,
    val birthDate: LocalDate,
    val country: Country,
) {
    constructor() : this(
        id = 0, firstname = "", middlename = "", lastname = "", email = "",
        phone = Phone(), image = Image(), birthDate = LocalDate.now(),
        country = Country()
    )
}