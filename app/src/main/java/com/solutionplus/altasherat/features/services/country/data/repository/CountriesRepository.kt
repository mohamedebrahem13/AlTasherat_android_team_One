package com.solutionplus.altasherat.features.services.country.data.repository

import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.services.country.domain.repository.remote.ICountriesRemoteDS

internal class CountriesRepository(
    private val remoteDS: ICountriesRemoteDS,
    private val localDS: ICountriesLocalDS
) : ICountriesRepository {

    override suspend fun getCountriesFromRemote(): List<Country> {
        val result = remoteDS.getCountries().data
        return CountryMapper.dtoToDomain(result)
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
        val result = localDS.getCountries()
        return CountryMapper.entityToDomain(result)
    }
}