package com.solutionplus.altasherat.features.splash.data.repository

import com.google.gson.Gson
import com.solutionplus.altasherat.features.splash.data.mapper.CountryMapper
import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ICountriesRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ICountriesLocalDS
import com.solutionplus.altasherat.features.splash.domain.repository.remote.ICountriesRemoteDS

class CountriesRepository (private val localDataSource: ICountriesLocalDS, private val remoteDataSource: ICountriesRemoteDS,private val countryMapper: CountryMapper):ICountriesRepository{
    override suspend fun getCountries(): CountriesResponse {
        val countryResponseDto = remoteDataSource.getCountries()
        return countryMapper.mapDtoToDomain(countryResponseDto.data!!)
    }

    override suspend fun saveCountries(countries: List<Country>){
        val countryEntities = countries.map { countryMapper.domainToEntity(it) }
        localDataSource.saveCountryString(countryEntities)
    }
}