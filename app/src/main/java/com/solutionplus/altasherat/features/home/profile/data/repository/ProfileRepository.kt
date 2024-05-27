package com.solutionplus.altasherat.features.home.profile.data.repository

import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource

class ProfileRepository (
    private val remoteDataSource: IProfileRemoteDataSource
):IProfileRepository{
    override suspend fun logout(): String {
        return  remoteDataSource.logout().message.toString()
    }
}