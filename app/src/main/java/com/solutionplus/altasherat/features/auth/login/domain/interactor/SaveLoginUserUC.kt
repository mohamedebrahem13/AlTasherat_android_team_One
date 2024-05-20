package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import javax.inject.Inject

class SaveLoginUserUC @Inject constructor(
    private val repository: LoginRepository
) : BaseUseCase<String, String>() {
    override suspend fun execute(params: String?): String {
        params?.let { repository.saveUserToken(token = it) }
        return "Token was Saved Successfully"
    }
}