package com.solutionplus.altasherat.features.services.token.domain.repository.local

internal interface ITokenLocalDS {
    suspend fun getToken(): ByteArray
    suspend fun deleteToken()
}