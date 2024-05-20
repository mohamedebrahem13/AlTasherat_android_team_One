package com.solutionplus.altasherat.features.auth.login.domain.repository

import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo

interface ILoginRepository {
    suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserInfo
    suspend fun saveUserToken(token: String)

}