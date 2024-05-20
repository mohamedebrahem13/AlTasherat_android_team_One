package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class FakeSplashRepository(
    private val remoteDS: ISplashRemoteDS,
    private val localDS: ISplashLocalDS,
    private val countryMapper: CountryMapper // Inject the CountryMapper here
) : ISplashRepository {

    private var shouldThrowException = false

    override suspend fun hasCountryStringKey(): Boolean {
        if (shouldThrowException) throw Exception("Error fetching countries")
        return localDS.hasCountryStringKey()
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
        val jsonString = localDS.getCountries()
        return  countryMapper.mapEntityListToDomain(jsonString)

    }

    override suspend fun getCountriesFromRemote(params: String): CountriesResponse {
        if (shouldThrowException) throw Exception("Error fetching countries")
        val countryResponseDto = remoteDS.getCountries(params)
        return countryMapper.mapDtoListToDomain(countryResponseDto.data)    }

    override suspend fun saveCountries(countries: List<Country>) {
        val countryEntities = countries.map { countryMapper.domainToEntity(it) }
        localDS.saveCountryString(countryEntities)
    }

    fun setShouldThrowException(shouldThrow: Boolean) {
        shouldThrowException = shouldThrow
    }

}