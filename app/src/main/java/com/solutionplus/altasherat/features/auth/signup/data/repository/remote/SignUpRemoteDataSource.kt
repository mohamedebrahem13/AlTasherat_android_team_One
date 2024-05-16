package com.solutionplus.altasherat.features.auth.signup.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.auth.signup.data.model.dto.UserResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import javax.inject.Inject

class SignUpRemoteDataSource @Inject constructor(
    private val netWorkProvider: INetworkProvider
): ISignUpRemoteDataSource {
    override suspend fun signup(userRequest: UserRequest): UserResponseDto {
        return netWorkProvider.post(
            responseWrappedModel = UserResponseDto::class.java,
            "signup",
            headers = hashMapOf("accept" to "application/json"),
            requestBody = userRequest
        ) ?:UserResponseDto()
    }
}