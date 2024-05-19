package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import android.util.Patterns
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException

class EmailValidationUC {

    fun execute(email: String) {
        if (email.length > 50) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""),
                "Your input exceeds the maximum allowed length of 50 characters. Please shorten your text"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw AlTasheratException.Client.ResponseValidation(
                hashMapOf("" to ""), "your input email doesn't match "
            )
        }

    }
}