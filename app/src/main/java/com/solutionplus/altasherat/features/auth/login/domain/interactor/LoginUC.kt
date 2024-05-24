package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val repository: LoginRepository,
) : BaseUseCase<LoginUserInfo, UserLoginRequest>() {

    override suspend fun execute(params: UserLoginRequest?): LoginUserInfo {
        val loginUserInfo = params?.let { repository.loginWithPhone(it) }!!
        repository.saveUser(loginUserInfo)
        repository.saveUserToken(loginUserInfo)
        return loginUserInfo
    }
}