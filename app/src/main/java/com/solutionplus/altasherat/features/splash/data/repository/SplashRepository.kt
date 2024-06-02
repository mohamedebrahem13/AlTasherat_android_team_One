package com.solutionplus.altasherat.features.splash.data.repository

import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashRepository (private val localDataSource: ISplashLocalDS):ISplashRepository{

    override suspend fun hasCountryStringKey(): Boolean {
        return localDataSource.hasCountryStringKey()
    }

    override suspend fun saveUserPreferredCountry(country: String) {
        localDataSource.saveUserPreferredCountry(country)
    }

    override suspend fun getUserPreferredCountry(): String {
        return localDataSource.getUserPreferredCountry()
    }

    override suspend fun saveUserPreferredLanguage(language: String) {
        localDataSource.saveUserPreferredLanguage(language)
    }

    override suspend fun getUserPreferredLanguage(): String {
      return localDataSource.getUserPreferredLanguage()
    }

    override suspend fun setOnboardingShown(shown: Boolean) {
        localDataSource.setOnboardingShown(shown)
    }

    override suspend fun isOnboardingShown(): Boolean {
      return  localDataSource.isOnboardingShown()
    }
}