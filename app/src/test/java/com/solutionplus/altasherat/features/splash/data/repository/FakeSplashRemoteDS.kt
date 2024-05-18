package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class FakeSplashRemoteDS(private val iNetworkProvider: INetworkProvider) : ISplashRemoteDS {
    override suspend fun getCountries(): CountryResponseDto {
        return iNetworkProvider.get(CountryResponseDto::class.java, "countries")
    }
}