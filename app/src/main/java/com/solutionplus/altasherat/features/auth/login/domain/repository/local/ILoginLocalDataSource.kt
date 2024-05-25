package com.solutionplus.altasherat.features.auth.login.domain.repository.local

import com.solutionplus.altasherat.features.auth.login.data.models.entity.LoginUserEntity

interface ILoginLocalDataSource {
    suspend fun saveUser(user: LoginUserEntity)
    suspend fun saveToken(token: String)

}