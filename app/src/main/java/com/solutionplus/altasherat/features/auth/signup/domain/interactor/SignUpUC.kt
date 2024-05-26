package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: SignUpRepository,
    private val userInputsValidationUC: UserInputsValidationUC,
) : BaseUseCase<UserResponse, UserSignUpRequest>() {
    override suspend fun execute(params: UserSignUpRequest?): UserResponse {
        val user = params?.let {
            userInputsValidationUC.validateUserInputs(it)
            repository.signup(it)
        }!!
        repository.saveUser(user.user)
        repository.saveUserToken(user.token)
        return user
    }


}














