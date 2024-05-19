package com.solutionplus.altasherat.features.auth.signup.domain.repository

import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

interface ISignUpRepository {
    suspend fun signup(userRequest: UserRequest): UserInfo
    suspend fun saveUser(user: String)

}