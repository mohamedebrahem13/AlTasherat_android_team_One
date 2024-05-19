package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

class PasswordValidation {
    fun execute(password: String) {
        if (password.isBlank()) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                "password is required"
            )
        }
        if (password.length < 8 || password.length > 50){
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                ""
            )
        }
    }
}