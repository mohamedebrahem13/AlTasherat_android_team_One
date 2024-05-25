package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.validation.UserInputsValidationUC
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: SignUpRepository,
    private val userInputsValidationUC: UserInputsValidationUC,
) : BaseUseCase<UserInfo, UserSignUpRequest>() {
    override suspend fun execute(params: UserSignUpRequest?): UserInfo {
        val userInfo = params?.let {
            userInputsValidationUC.validateUserInputs(it)
            repository.signup(it)
        }!!
        repository.saveUser(userInfo)
        repository.saveUserToken(userInfo.token.orEmpty())
        return userInfo
    }


}














