package com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation

import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest

class UserInputsValidation(
    private val emailValidationUC: EmailValidationUC = EmailValidationUC(),
    private val firstAndLastNameValidation: FirstAndLastNameValidation = FirstAndLastNameValidation(),
    private val passwordValidation: PasswordValidation = PasswordValidation(),
    private val phoneNumberValidation: PhoneNumberValidation = PhoneNumberValidation()
) {

    fun userInputsValidation(userRequest: UserRequest) {
        emailValidationUC.execute(userRequest.email)
        firstAndLastNameValidation.execute(userRequest.firstname)
        firstAndLastNameValidation.execute(userRequest.lastname)
        passwordValidation.execute(userRequest.password)
        phoneNumberValidation.execute(userRequest.phoneRequest.number)
    }
}