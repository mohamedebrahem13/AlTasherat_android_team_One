package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRepository (private val localDataSource: ISplashLocalDS, private val remoteDataSource: ISplashRemoteDS, private val countryMapper: CountryMapper):ISplashRepository{
    override suspend fun getCountriesFromRemote(locale: String): CountriesResponse {
        val countryResponseDto = remoteDataSource.getCountries(locale)
        return countryMapper.mapDtoListToDomain(countryResponseDto.data)    }

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

    override suspend fun saveUserPreferredCountry(country: String) {
        localDataSource.saveUserPreferredCountry(country)
    }

    override suspend fun getUserPreferredCountry(): String {
        return localDataSource.getUserPreferredCountry()
    }

    override suspend fun saveUserPreferredLanguage(language: String) {
        localDataSource.saveUserPreferredLanguage(language)
    }

    override suspend fun getUserPreferredLanguage(): String {
      return localDataSource.getUserPreferredLanguage()
    }
}