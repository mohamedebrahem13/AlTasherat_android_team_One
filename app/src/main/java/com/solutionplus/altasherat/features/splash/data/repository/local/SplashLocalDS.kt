package com.solutionplus.altasherat.features.splash.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashLocalDS(private val preferenceStorage: IKeyValueStorageProvider):ISplashLocalDS {

    override suspend fun hasCountryStringKey(): Boolean {
        return preferenceStorage.hasKey<String>(StorageKeyEnum.COUNTRIES_STRING, String::class.java)
    }


    override suspend fun saveUserPreferredCountry(country: String) {
        preferenceStorage.save(key = StorageKeyEnum.USER_PREFERRED_COUNTRY, value = country, type = String::class.java)
    }

    override suspend fun getUserPreferredCountry(): String {
        return preferenceStorage.get(StorageKeyEnum.USER_PREFERRED_COUNTRY, "", String::class.java)
    }

    override suspend fun saveUserPreferredLanguage(language: String) {
        preferenceStorage.save(key = StorageKeyEnum.LANGUAGE, value = language, type = String::class.java)
    }

    override suspend fun getUserPreferredLanguage(): String {
        return preferenceStorage.get(StorageKeyEnum.LANGUAGE, "ar", String::class.java)
    }

    override suspend fun setOnboardingShown(shown: Boolean) {
        preferenceStorage.save(key = StorageKeyEnum.ONBOARDING_SHOWN, value = shown,Boolean::class.java)
    }

    override suspend fun isOnboardingShown(): Boolean {
        return preferenceStorage.get(key = StorageKeyEnum.ONBOARDING_SHOWN, defaultValue = false,Boolean::class.java)
    }

}