package com.solutionplus.altasherat.features.auth.login.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.login.data.models.dto.ResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

internal class LoginRemoteDataSource @Inject constructor(
    private val networkProvider: INetworkProvider
) : ILoginRemoteDataSource {
    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): ResponseDto {
        return networkProvider.post(
            responseWrappedModel = ResponseDto::class.java,
            pathUrl = "login",
            requestBody = userLoginRequest
        )
    }
}