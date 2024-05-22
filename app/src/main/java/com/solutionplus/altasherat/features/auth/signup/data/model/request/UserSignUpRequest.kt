package com.solutionplus.altasherat.features.auth.signup.data.model.request

import android.util.Patterns
import com.google.gson.annotations.SerializedName

data class UserSignUpRequest(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("middlename")
    val middleName: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("phone")
    val phoneSignUpRequest: PhoneSignUpRequest
) {
    fun validateEmail(): Boolean {
        return !(email.length > 50 || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validateFirstName(): Boolean {
        return !(firstname.isBlank() || firstname.length < 3 || firstname.length > 15)
    }

    fun validateLastName(): Boolean {
        return !(lastname.isBlank() || lastname.length < 3 || lastname.length > 15)
    }

    fun validatePassword(): Boolean {
        return !(password.isBlank() || password.length < 8 || password.length > 50)
    }
}
