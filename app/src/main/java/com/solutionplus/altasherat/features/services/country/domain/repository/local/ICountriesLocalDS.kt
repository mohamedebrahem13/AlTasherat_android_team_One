package com.solutionplus.altasherat.features.services.country.domain.repository.local

import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity

internal interface ICountriesLocalDS {
    suspend fun getCountries(): List<CountryEntity>
}