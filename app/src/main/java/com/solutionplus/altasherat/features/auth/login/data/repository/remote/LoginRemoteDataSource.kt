package com.solutionplus.altasherat.features.auth.login.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.models.dto.LoginResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val networkProvider: INetworkProvider
) : ILoginRemoteDataSource {
    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginResponseDto {
        return networkProvider.post(
            responseWrappedModel = LoginResponseDto::class.java,
            pathUrl = "login",
            headers = hashMapOf("accept" to "application/json"),
            requestBody = userLoginRequest
        )
    }
}