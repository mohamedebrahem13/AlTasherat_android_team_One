package com.solutionplus.altasherat.features.splash.domain.repository.local

import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity

internal interface ISplashLocalDS {
    suspend fun saveCountryString(countries: List<CountryEntity>)
    suspend fun hasCountryStringKey(): Boolean

}