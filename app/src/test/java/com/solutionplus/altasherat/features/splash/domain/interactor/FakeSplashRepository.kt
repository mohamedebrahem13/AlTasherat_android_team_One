package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class FakeSplashRepository(
    private val localDS: ISplashLocalDS,
) : ISplashRepository {

    private var shouldThrowException = false

    override suspend fun hasCountryStringKey(): Boolean {
        if (shouldThrowException) throw Exception("Error fetching countries")
        return localDS.hasCountryStringKey()
    }



    override suspend fun saveUserPreferredCountry(country: String) {
        if (shouldThrowException) throw Exception("Error saving preferred country")
        localDS.saveUserPreferredCountry(country)
    }

    override suspend fun getUserPreferredCountry(): String {
        return localDS.getUserPreferredCountry()
    }

    override suspend fun saveUserPreferredLanguage(language: String) {
        if (shouldThrowException) throw Exception("Error saving preferred language")
        localDS.saveUserPreferredLanguage(language)
    }

    override suspend fun getUserPreferredLanguage(): String {
        return localDS.getUserPreferredLanguage()
    }

    override suspend fun setOnboardingShown(shown: Boolean) {
        if (shouldThrowException) throw Exception("Error setting onboarding shown")
        localDS.setOnboardingShown(shown)
    }

    override suspend fun isOnboardingShown(): Boolean {
        return localDS.isOnboardingShown()
    }

    fun setShouldThrowException(shouldThrow: Boolean) {
        shouldThrowException = shouldThrow
    }

}