package com.solutionplus.altasherat.features.auth.signup.data.model.request

import androidx.core.util.PatternsCompat
import com.google.gson.annotations.SerializedName
import java.util.regex.Pattern

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
    @SerializedName("country")
    val countryId: Int,
    @SerializedName("phone")
    val phoneSignUpRequest: PhoneSignUpRequest
) {

    private val regex = Pattern.compile("[^a-zA-Z0-9 ]")
    fun validateEmail(): Boolean {
        return !(
                email.length > 50 ||
                        !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

                )
    }

    fun validateFirstName(): Boolean {
        return !(firstname.isBlank() || firstname.length < 3 || firstname.length > 15 ||  regex.matcher(firstname).find())
    }

    fun validateLastName(): Boolean {
        return !(lastname.isBlank() || lastname.length < 3 || lastname.length > 15 ||  regex.matcher(firstname).find())
    }

    fun validatePassword(): Boolean {
        return !(
                password.isBlank() ||
                        password.length < 8 ||
                        password.length > 50 ||
                        !password.any { it.isUpperCase() } ||
                        !password.any { it.isLowerCase() } ||
                        !password.any { it.isDigit() }
                )
    }
}
