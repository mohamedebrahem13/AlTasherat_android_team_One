package com.solutionplus.altasherat.features.auth.login.domain.repository.local

interface ILoginLocalDataSource {
    suspend fun saveUserToken(token: String)
}