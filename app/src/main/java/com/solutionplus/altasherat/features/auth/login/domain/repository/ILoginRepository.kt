package com.solutionplus.altasherat.features.auth.login.domain.repository

import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo

interface ILoginRepository {
    suspend fun loginWithPhone(loginRequest: LoginRequest): LoginUserInfo
    suspend fun saveUserToken(token: String)

}