package com.solutionplus.altasherat.features.auth.signup.domain.repository.local

import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity

interface ISignUpLocalDataSource {
    suspend fun saveUser(user: SignUpUserEntity)
    suspend fun saveUserToken(token: String)
}