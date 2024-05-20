package com.solutionplus.altasherat.features.splash.domain.repository.remote

import com.solutionplus.altasherat.features.splash.data.models.dto.CountryResponseDto

internal interface ISplashRemoteDS {
    suspend fun getCountries(params: String): CountryResponseDto
}