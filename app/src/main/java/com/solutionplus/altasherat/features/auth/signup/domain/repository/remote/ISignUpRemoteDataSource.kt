package com.solutionplus.altasherat.features.auth.signup.domain.repository.remote

import com.solutionplus.altasherat.features.auth.signup.data.model.dto.SignUpResponseDto
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest

interface ISignUpRemoteDataSource {

    suspend fun signup(userSignUpRequest: UserSignUpRequest): SignUpResponseDto
}