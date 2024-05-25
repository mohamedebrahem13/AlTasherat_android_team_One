package com.solutionplus.altasherat.features.splash.data.repository.local

import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.android.extentions.toJson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class FakeSplashLocalDS(private val preferenceStorage: IKeyValueStorageProvider) :
    ISplashLocalDS {
    override suspend fun saveCountryString(countries: List<CountryEntity>) {
        val countriesString = countries.toJson()
        preferenceStorage.save(StorageKeyEnum.COUNTRIES_STRING, countriesString, String::class.java)
    }

    override suspend fun hasCountryStringKey(): Boolean {
        return preferenceStorage.hasKey<String>(StorageKeyEnum.COUNTRIES_STRING, String::class.java)
    }

    override suspend fun getCountries(): List<CountryEntity> {
        val countriesJson = preferenceStorage.get(
            StorageKeyEnum.COUNTRIES_STRING,
            "",
            String::class.java
        )
        // Define Type Token for a List of CountryEntity
        val countryListType = object : TypeToken<List<CountryEntity>>() {}.type
        return  countriesJson.getModelFromJSON(countryListType)
    }

    override suspend fun saveUserPreferredCountry(country: String) {
        preferenceStorage.save(key = StorageKeyEnum.USER_PREFERRED_COUNTRY, value = country, type = String::class.java)
    }

    override suspend fun getUserPreferredCountry(): String {
        return preferenceStorage.get(StorageKeyEnum.USER_PREFERRED_COUNTRY, "السعودية", String::class.java)
    }

    override suspend fun saveUserPreferredLanguage(language: String) {
        preferenceStorage.save(key = StorageKeyEnum.LANGUAGE, value = language, type = String::class.java)
    }

    override suspend fun getUserPreferredLanguage(): String {
        return preferenceStorage.get(StorageKeyEnum.LANGUAGE, "ar", String::class.java)
    }

    override suspend fun setOnboardingShown(shown: Boolean) {
        preferenceStorage.save(key = StorageKeyEnum.ONBOARDING_SHOWN, value = shown, type = Boolean::class.java)
    }

    override suspend fun isOnboardingShown(): Boolean {
        return preferenceStorage.get(StorageKeyEnum.ONBOARDING_SHOWN, false, Boolean::class.java)
    }
}