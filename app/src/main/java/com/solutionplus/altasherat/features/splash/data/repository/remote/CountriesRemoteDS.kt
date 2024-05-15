package com.solutionplus.altasherat.features.splash.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ICountriesRemoteDS

class CountriesRemoteDS (private val iNetworkProvider: INetworkProvider):ICountriesRemoteDS{

    override suspend fun getCountries(): CountryResponseDto {
        return iNetworkProvider.get( responseWrappedModel = CountryResponseDto::class.java,
            "countries"
            )
    }
}