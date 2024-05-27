package com.solutionplus.altasherat.features.home.profile.domain.repository.remote

import com.solutionplus.altasherat.features.home.profile.data.models.LogoutResponse

interface IProfileRemoteDataSource {
    suspend fun logout(): LogoutResponse
}