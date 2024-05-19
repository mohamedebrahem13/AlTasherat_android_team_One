package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import javax.inject.Inject

class SaveLocalUserUC @Inject constructor(
    private val repository: SignUpRepository
): BaseUseCase<String, String>() {
    override suspend fun execute(params: String?): String {
         params?.let { repository.saveUser(user = it) }
        return "User Saved Successfully"
    }
}