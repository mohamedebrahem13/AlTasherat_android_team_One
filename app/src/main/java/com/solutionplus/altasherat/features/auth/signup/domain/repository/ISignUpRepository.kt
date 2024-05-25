package com.solutionplus.altasherat.features.auth.signup.domain.repository

import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo

interface ISignUpRepository {
    suspend fun signup(userSignUpRequest: UserSignUpRequest): UserInfo
    suspend fun saveUser(userInfo: UserInfo)
    suspend fun saveUserToken(token: String)

}