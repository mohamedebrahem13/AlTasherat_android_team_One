package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

class FirstAndLastNameValidation {
    fun execute(username: String) {
        if (username.isBlank()) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                "first name is required"
            )
        }
        if (username.length < 3 || username.length > 15) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                ""
            )
        }

    }
}