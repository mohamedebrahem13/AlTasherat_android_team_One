package com.solutionplus.altasherat.features.auth.login.domain.repository.local

interface ILoginLocalDataSource {
    suspend fun saveUser(user: String)
    suspend fun saveToken(token: String)

}