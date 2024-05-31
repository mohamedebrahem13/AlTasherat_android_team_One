package com.solutionplus.altasherat.features.services.country.data.repository.local

import com.google.gson.reflect.TypeToken
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.android.extentions.toJson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.repository.local.ICountriesLocalDS

internal class CountriesLocalDS(
    private val keyValueStorage: IKeyValueStorageProvider
) : ICountriesLocalDS {

    override suspend fun getCountries(): List<CountryEntity> {
        val countriesJson = keyValueStorage.get(
            StorageKeyEnum.COUNTRIES_STRING,
            "",
            String::class.java
        )
        val itemType = object : TypeToken<List<CountryEntity>>() {}.type
        return countriesJson.getModelFromJSON(itemType) ?: emptyList()
    }

    override suspend fun saveCountryString(countries: List<CountryEntity>) {
        val countriesString= countries.toJson()
        keyValueStorage.save(key = StorageKeyEnum.COUNTRIES_STRING, value = countriesString, type = String::class.java)    }
}