package com.solutionplus.altasherat.features.services.country.domain.repository.remote

import com.solutionplus.altasherat.features.services.country.data.models.dto.CountriesDto

internal interface ICountriesRemoteDS {
    suspend fun getCountries(): CountriesDto
}