package com.solutionplus.altasherat.features.auth.login.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val netWorkProvider: INetworkProvider
): ILoginRemoteDataSource {
    override suspend fun loginWithPhone(loginRequest: LoginRequest): LoginResponseDto {
        return netWorkProvider.post(
            responseWrappedModel = LoginResponseDto::class.java,
            pathUrl = "login",
            headers = hashMapOf("accept" to "application/json"),
            requestBody = loginRequest
            )
    }
}