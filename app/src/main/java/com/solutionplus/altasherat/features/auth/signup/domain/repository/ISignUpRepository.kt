package com.solutionplus.altasherat.features.auth.signup.domain.repository

import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse

interface ISignUpRepository {
    suspend fun signup(userSignUpRequest: UserSignUpRequest): UserResponse
    suspend fun saveUserToken(token: String)

}