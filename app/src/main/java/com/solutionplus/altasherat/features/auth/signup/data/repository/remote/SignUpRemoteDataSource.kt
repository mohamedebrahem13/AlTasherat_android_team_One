package com.solutionplus.altasherat.features.auth.signup.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import javax.inject.Inject

internal class SignUpRemoteDataSource @Inject constructor(
    private val netWorkProvider: INetworkProvider
) : ISignUpRemoteDataSource {
    override suspend fun signup(userSignUpRequest: UserSignUpRequest): SignUpResponseDto {
        return netWorkProvider.post(
            responseWrappedModel = SignUpResponseDto::class.java,
            "signup",
            requestBody = userSignUpRequest
        )
    }
}