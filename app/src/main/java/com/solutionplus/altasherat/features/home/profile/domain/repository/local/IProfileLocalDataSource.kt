package com.solutionplus.altasherat.features.home.profile.domain.repository.local

internal interface IProfileLocalDataSource {
    suspend fun deleteUserInfo()
    suspend fun deleteUserToken()
    suspend fun hasTokenKey(): Boolean

}