package com.solutionplus.altasherat.features.splash.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class FakeSplashRemoteDS(private val networkProvider: INetworkProvider) : ISplashRemoteDS {
    override suspend fun getCountries(params: String): CountryResponseDto {
        return networkProvider.get(CountryResponseDto::class.java, "countries")
    }
}