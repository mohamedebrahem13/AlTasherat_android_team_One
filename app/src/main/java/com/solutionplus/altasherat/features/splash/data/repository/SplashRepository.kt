package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRepository (private val localDataSource: ISplashLocalDS, private val remoteDataSource: ISplashRemoteDS, private val countryMapper: CountryMapper):ISplashRepository{
    override suspend fun getCountries(): CountriesResponse {
        val countryResponseDto = remoteDataSource.getCountries()
        return countryMapper.mapDtoToDomain(countryResponseDto.data!!)
    }

    override suspend fun saveCountries(countries: List<Country>){
        val countryEntities = countries.map { countryMapper.domainToEntity(it) }
        localDataSource.saveCountryString(countryEntities)
    }

    override suspend fun hasCountryStringKey(): Boolean {
        return localDataSource.hasCountryStringKey()
    }
}