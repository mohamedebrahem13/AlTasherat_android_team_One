package com.solutionplus.altasherat.features.splash.domain.repository.local

internal interface ISplashLocalDS {
    suspend fun hasCountryStringKey(): Boolean
    // Methods to save user preferences for country and language
    suspend fun saveUserPreferredCountry(country: String)
    suspend fun getUserPreferredCountry(): String
    suspend fun saveUserPreferredLanguage(language: String)
    suspend fun getUserPreferredLanguage(): String

    // Methods to save and get onboarding shown status
    suspend fun setOnboardingShown(shown: Boolean)
    suspend fun isOnboardingShown(): Boolean
}