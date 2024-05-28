package com.solutionplus.altasherat.features.home.profile.domain.repository.local

interface IProfileLocalDataSource {
    suspend fun deleteUserInfo()
    suspend fun deleteUserToken()
}