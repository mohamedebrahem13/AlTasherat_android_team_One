package com.solutionplus.altasherat.features.services.user.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserResponseDto
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS
import com.solutionplus.altasherat.features.services.user.domain.repository.remote.IUserRemoteDS

internal class UserRemoteDS(
    private val networkProvider: INetworkProvider,
) : IUserRemoteDS {

    override suspend fun getUser(): UserResponseDto {
        return networkProvider.get(
            pathUrl = "show-account",
            headers = mapOf("Authorization-Required" to "true"),
            responseWrappedModel = UserResponseDto::class.java
        )
    }
}