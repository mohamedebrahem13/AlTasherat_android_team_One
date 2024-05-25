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
                "Your input first name is not valid"
            )
        }

        if (!userSignUpRequest.validateLastName()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Your input last name is not valid"
            )
        }

        if (!userSignUpRequest.validatePassword()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Your input password is not valid"
            )
        }

        if (!userSignUpRequest.phoneSignUpRequest.validatePhoneNumber()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Your input phone is not valid"
            )
        }

    }


}