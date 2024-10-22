package com.solutionplus.altasherat.features.account.personal_info.data.models.request

import androidx.core.util.PatternsCompat
import com.google.gson.annotations.SerializedName
import java.io.File

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
    val image: File? = null,
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

    fun isPhoneValid(): Boolean {
        return phone.isNumberValid() && phone.isCountryCodeValid()
    }
}