package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse

class LoginWithPhoneUC(
    private val repository: LoginRepository,
) : BaseUseCase<LoginUserResponse, UserLoginRequest>() {

    override suspend fun execute(params: UserLoginRequest?): LoginUserResponse {
        val loginUserResponse = params?.let { repository.loginWithPhone(it) }!!
        repository.saveUser(loginUserResponse.user)
        repository.saveUserToken(loginUserResponse.token)
        return loginUserResponse
    }
}