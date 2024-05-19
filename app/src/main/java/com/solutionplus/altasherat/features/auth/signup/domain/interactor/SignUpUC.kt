package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.EmailValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.FirstAndLastNameValidation
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.PasswordValidation
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.PhoneNumberValidation
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidation
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: SignUpRepository,
    private val userInputsValidation: UserInputsValidation = UserInputsValidation()
): BaseUseCase<UserInfo, UserRequest>() {
    override suspend fun execute(params: UserRequest?): UserInfo {
        params?.let { userInputsValidation.userInputsValidation(it) }
        return params?.let { repository.signup(it) }!!
    }

}