package com.solutionplus.altasherat.features.auth.login.domain.repository.remote

import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest


interface ILoginRemoteDataSource {
    suspend fun loginWithPhone(loginRequest: LoginRequest): LoginResponseDto
}