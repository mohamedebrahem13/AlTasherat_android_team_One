package com.solutionplus.altasherat.android.extentions

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.parseDateString(): LocalDate {
    val defaultZoneFormatter =
        DateTimeFormatter.ofPattern("yyyy-M-d").withZone(ZoneId.systemDefault())
    return if (this.isEmpty()) LocalDate.now() else LocalDate.parse(this, defaultZoneFormatter)
}