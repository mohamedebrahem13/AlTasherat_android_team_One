package com.solutionplus.altasherat.features.splash.domain.repository.local

import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity

internal interface ISplashLocalDS {
    suspend fun saveCountryString(countries: List<CountryEntity>)
    suspend fun hasCountryStringKey(): Boolean
    suspend fun getCountries(): List<CountryEntity>
    // Methods to save user preferences for country and language
    suspend fun saveUserPreferredCountry(country: String)
    suspend fun getUserPreferredCountry(): String
    suspend fun saveUserPreferredLanguage(language: String)
    suspend fun getUserPreferredLanguage(): String
}