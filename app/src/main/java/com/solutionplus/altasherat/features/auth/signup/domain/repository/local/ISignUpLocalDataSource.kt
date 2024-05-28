package com.solutionplus.altasherat.features.auth.signup.domain.repository.local

internal interface ISignUpLocalDataSource {
    suspend fun saveUserToken(token: String)
}