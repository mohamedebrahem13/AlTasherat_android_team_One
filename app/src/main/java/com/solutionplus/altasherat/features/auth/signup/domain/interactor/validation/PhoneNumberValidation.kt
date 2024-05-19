package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

class PhoneNumberValidation {
    fun execute(phoneNumber: String) {
        if (phoneNumber.any { !it.isDigit() }) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                "first name must be digits"
            )
        }
        if (phoneNumber.isBlank()) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                "phone number is required"
            )
        }
        if (phoneNumber.length < 9 || phoneNumber.length > 15) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                ""
            )
        }
    }
}