package com.solutionplus.altasherat.features.splash.domain.repository.remote

import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto

interface ICountriesRemoteDS {
    suspend fun getCountries(): CountryResponseDto

}