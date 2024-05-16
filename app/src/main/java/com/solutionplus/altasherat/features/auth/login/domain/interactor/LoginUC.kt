package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import javax.inject.Inject

class LoginUC @Inject constructor(
    private val repository: LoginRepository
): BaseUseCase<LoginUserInfo, LoginRequest>() {

    override suspend fun execute(params: LoginRequest?): LoginUserInfo {
        return params?.let { repository.loginWithPhone(it) }!!
    }
}