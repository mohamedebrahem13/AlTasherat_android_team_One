package com.solutionplus.altasherat.features.auth.login.domain.repository.remote

import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest


interface ILoginRemoteDataSource {
    suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginResponseDto
}