package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest

class UserInputsValidationUC {

    fun validateUserInputs(userSignUpRequest: UserSignUpRequest) {
        if (!userSignUpRequest.validateEmail()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Your input email is not valid"
            )
        }

        if (!userSignUpRequest.validateFirstName()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "First name must be between 3 and 15 & doesn't contain Special Characters."
            )
        }

        if (!userSignUpRequest.validateLastName()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Last name must be between 3 and 15 & doesn't contain Special Characters."
            )
        }

        if (!userSignUpRequest.validatePassword()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Password must be between 8 and 50 & contain Lower&Upper Character"

            )
        }

        if (!userSignUpRequest.phoneSignUpRequest.validatePhoneNumber()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Phone must be between 9 and 15 digits"

            )
        }

    }


}