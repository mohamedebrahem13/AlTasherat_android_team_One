package com.solutionplus.altasherat.features.services.token.domain.repository

interface ITokenRepository {
    suspend fun getTokenFromLocal(): ByteArray
    suspend fun deleteToken()
}