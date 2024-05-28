package com.solutionplus.altasherat.features.home.profile.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.home.profile.data.models.LogoutResponse
import com.solutionplus.altasherat.features.home.profile.domain.repository.remote.IProfileRemoteDataSource
import javax.inject.Inject

internal class ProfileRemoteDataSource (
    private val networkProvider: INetworkProvider
) :IProfileRemoteDataSource{
    override suspend fun logout(): LogoutResponse {
        return networkProvider.delete(
            responseWrappedModel = LogoutResponse::class.java,
            pathUrl = "logout",
            headers = hashMapOf("accept" to "application/json"),
        )
    }
}