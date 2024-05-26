package com.solutionplus.altasherat.features.auth.login.domain.repository

import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse

interface ILoginRepository {
    suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserResponse
    suspend fun saveUser(user: LoginUserInfo)
    suspend fun saveUserToken(token: String)

}