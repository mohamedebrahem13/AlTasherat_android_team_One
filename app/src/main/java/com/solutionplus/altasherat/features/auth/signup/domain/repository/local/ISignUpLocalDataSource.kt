package com.solutionplus.altasherat.features.auth.signup.domain.repository.local

interface ISignUpLocalDataSource {
    suspend fun saveUser(user: String)
    suspend fun saveUserToken(token: String)
}