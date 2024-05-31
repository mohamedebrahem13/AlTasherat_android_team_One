package com.solutionplus.altasherat.features.splash.domain.repository

import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country

interface ISplashRepository {
        suspend fun hasCountryStringKey(): Boolean
        suspend fun saveUserPreferredCountry(country: String)
        suspend fun getUserPreferredCountry(): String
        suspend fun saveUserPreferredLanguage(language: String)
        suspend fun getUserPreferredLanguage(): String
        suspend fun setOnboardingShown(shown: Boolean)
        suspend fun isOnboardingShown(): Boolean
}