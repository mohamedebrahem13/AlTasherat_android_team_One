package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import javax.inject.Inject

class SignUpUC @Inject constructor(
    private val repository: SignUpRepository
): BaseUseCase<UserInfo, UserRequest>() {

    override suspend fun execute(params: UserRequest?): UserInfo {
        return params?.let { repository.signup(it) }!!
    }
}