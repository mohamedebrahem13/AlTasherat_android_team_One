package com.solutionplus.altasherat.features.splash.domain.repository

import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country

interface ISplashRepository {
        suspend fun getCountriesFromRemote(params: String): CountriesResponse // Change the return type as needed
        suspend fun saveCountries(countries: List<Country>)
        suspend fun hasCountryStringKey(): Boolean
        suspend fun getCountriesFromLocal(): List<Country>

        // New methods for user preferences
        suspend fun saveUserPreferredCountry(country: String)
        suspend fun getUserPreferredCountry(): String
        suspend fun saveUserPreferredLanguage(language: String)
        suspend fun getUserPreferredLanguage(): String
}