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
        if (countriesJson != null) {
            return  countriesJson.getModelFromJSON(countryListType)
        }
        return emptyList()
    }
}