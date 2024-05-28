package com.solutionplus.altasherat.features.home.profile.data.repository

import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import com.solutionplus.altasherat.features.home.profile.domain.repository.local.IProfileLocalDataSource
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource
import javax.inject.Inject

internal class ProfileRepository @Inject constructor (
    private val remoteDataSource: IProfileRemoteDataSource,
    private val localDataSource:IProfileLocalDataSource
):IProfileRepository{
    override suspend fun logout(): String {
        return  remoteDataSource.logout().message ?: "Logout successful"

    }

    override suspend fun deleteUserInfo() {
        localDataSource.deleteUserInfo()
    }

    override suspend fun deleteUserToken() {
        localDataSource.deleteUserToken()
    }
}