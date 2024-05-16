package com.solutionplus.altasherat.features.splash.domain.repository.local

import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity

interface ICountriesLocalDS {
    suspend fun saveCountryString(countries: List<CountryEntity>)

}