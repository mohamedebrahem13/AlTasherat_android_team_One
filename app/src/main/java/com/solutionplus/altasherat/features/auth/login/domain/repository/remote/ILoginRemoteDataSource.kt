package com.solutionplus.altasherat.features.auth.login.domain.repository.remote

import com.solutionplus.altasherat.features.services.user.data.models.dto.ResponseDto
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest


internal interface ILoginRemoteDataSource {
    suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): ResponseDto
}