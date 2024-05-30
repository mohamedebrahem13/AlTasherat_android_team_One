package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.interactor.validation.LoginInputValidation
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class LoginWithPhoneUC(
    private val repository: ILoginRepository,
    private val saveUserUC: SaveUserUC,
    private val loginInputValidation: LoginInputValidation
) : BaseUseCase<LoginUserResponse, UserLoginRequest>() {

    override suspend fun execute(params: UserLoginRequest?): LoginUserResponse {
        val loginUserResponse = params?.let {
            loginInputValidation.validateLoginInputs(it)
            repository.loginWithPhone(it)
        }!!
        saveUserUC.execute(loginUserResponse.user)
        repository.saveUserToken(loginUserResponse.token)
        return loginUserResponse
    }
}