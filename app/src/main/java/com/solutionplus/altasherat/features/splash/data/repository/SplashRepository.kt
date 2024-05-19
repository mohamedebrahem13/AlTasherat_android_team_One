package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRepository (private val localDataSource: ISplashLocalDS, private val remoteDataSource: ISplashRemoteDS, private val countryMapper: CountryMapper):ISplashRepository{
    override suspend fun getCountriesFromRemote(): CountriesResponse {
        val countryResponseDto = remoteDataSource.getCountries()
        return countryMapper.mapDtoListToDomain(countryResponseDto.data)
    }

    override suspend fun saveCountries(countries: List<Country>) {
        val countryEntities = countryMapper.mapDomainListToEntity(countries)
        localDataSource.saveCountryString(countryEntities)
    }
    override suspend fun hasCountryStringKey(): Boolean {
        return localDataSource.hasCountryStringKey()
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
       val countries= localDataSource.getCountries()
      return  countryMapper.mapEntityListToDomain(countries)
    }
}