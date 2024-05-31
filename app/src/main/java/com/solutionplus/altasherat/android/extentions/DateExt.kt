package com.solutionplus.altasherat.android.extentions

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.parseDateString(): LocalDate {
    val defaultZoneFormatter =
        DateTimeFormatter.ofPattern("yyyy-M-d").withZone(ZoneId.systemDefault())
    return if (this.isEmpty()) LocalDate.MIN else LocalDate.parse(this, defaultZoneFormatter)
}

fun LocalDate.parseLocalDate(): String {
    return if (this == LocalDate.MIN) "" else this.toString()
}