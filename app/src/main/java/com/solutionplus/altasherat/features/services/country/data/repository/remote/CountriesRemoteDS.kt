package com.solutionplus.altasherat.features.services.country.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountriesDto
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS
internal class CountriesRemoteDS(
    private val networkProvider: INetworkProvider
) : ICountriesRemoteDS {

    override suspend fun getCountries(params: String): CountriesDto {
        val headers = mapOf("X-locale" to params)

        return networkProvider.get( responseWrappedModel = CountriesDto::class.java,
            pathUrl = "countries",
            headers = headers
        )
    }
}