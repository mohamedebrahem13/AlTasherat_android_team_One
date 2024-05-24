package com.solutionplus.altasherat.features.personal_info.data.models.request

import androidx.core.util.PatternsCompat
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class UpdateInfoRequest(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("middlename")
    val middlename: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("birthdate")
    val birthDate: String,
    @SerializedName("phone")
    val phone: PhoneRequest,
    @SerializedName("image")
    val image: String,
    @SerializedName("country")
    val countryId: Int,
) {
    fun isFirstNameValid(): Boolean {
        return Regex("^[a-zA-Z]{3,15}$").matches(firstname)
    }

    fun isMiddleNameValid(): Boolean {
        return Regex("^[a-zA-Z]{0,15}$").matches(middlename)
    }

    fun isLastNameValid(): Boolean {
        return Regex("^[a-zA-Z]{3,15}$").matches(lastname)
    }

    fun isEmailValid(): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() && email.length <= 50
    }

    fun isBirthDateValid(): Boolean {
        return Regex("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$").matches(birthDate) && parseDate(
            birthDate
        ).isBefore(LocalDate.now())
    }

    fun isPhoneValid(): Boolean {
        return phone.isNumberValid() && phone.isCountryCodeValid()
    }

    fun isImageValid(): Boolean {
        return true
    }

    fun isCountryIdValid(): Boolean {
        return countryId > 0
    }

    private fun parseDate(date: String): LocalDate {
        if (date.isEmpty()) return LocalDate.now()
        return LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("yyyy-M-d").withZone(ZoneId.systemDefault())
        )
    }
}