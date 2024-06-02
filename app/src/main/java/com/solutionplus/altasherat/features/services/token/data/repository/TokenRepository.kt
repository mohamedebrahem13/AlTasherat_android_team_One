package com.solutionplus.altasherat.features.services.token.data.repository

import com.solutionplus.altasherat.features.services.token.domain.repository.ITokenRepository
import com.solutionplus.altasherat.features.services.token.domain.repository.local.ITokenLocalDS

internal class TokenRepository(
    private val localDS: ITokenLocalDS
) : ITokenRepository {

    override suspend fun getTokenFromLocal(): ByteArray {
        return localDS.getToken()
    }

    override suspend fun deleteToken() {
        localDS.deleteToken()
    }
}