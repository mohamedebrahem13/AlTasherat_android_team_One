package com.solutionplus.altasherat.features.auth.login.domain.interactor.validation

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest

class LoginInputValidation {

    fun validateLoginInputs(userLoginRequest: UserLoginRequest) {
        if (!userLoginRequest.validatePassword()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Password must be between 8 and 50 & contain Lower&Upper Character"
            )
        }

        if (!userLoginRequest.phoneLoginRequest.validatePhoneNumber()) {
            throw AlTasheratException.Local.RequestValidation(
                UserSignUpRequest::class,
                "Phone must be between 9 and 15 digits"
            )
        }
    }

}