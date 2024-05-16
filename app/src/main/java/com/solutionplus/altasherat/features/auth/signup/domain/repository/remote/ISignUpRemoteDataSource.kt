package com.solutionplus.altasherat.features.auth.signup.domain.repository.remote

import com.solutionplus.altasherat.features.auth.signup.data.model.dto.UserResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest

interface ISignUpRemoteDataSource {

    suspend fun signup(userRequest: UserRequest): UserResponseDto
}