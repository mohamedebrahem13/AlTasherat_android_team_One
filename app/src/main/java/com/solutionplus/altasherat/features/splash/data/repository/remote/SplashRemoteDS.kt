package com.solutionplus.altasherat.features.splash.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRemoteDS (private val iNetworkProvider: INetworkProvider):ISplashRemoteDS{

    override suspend fun getCountries(): CountryResponseDto {
        val headers = mapOf("X-locale" to "ar")

        return iNetworkProvider.get( responseWrappedModel = CountryResponseDto::class.java,
            pathUrl = "countries",
            headers = headers
            )
    }
}