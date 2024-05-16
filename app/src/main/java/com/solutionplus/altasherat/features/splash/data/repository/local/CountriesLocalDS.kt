package com.solutionplus.altasherat.features.splash.data.repository.local

import com.solutionplus.altasherat.android.extentions.toJson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.splash.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.splash.domain.repository.local.ICountriesLocalDS

class CountriesLocalDS(private val preferenceStorage: IKeyValueStorageProvider):ICountriesLocalDS {
    override suspend fun saveCountryString(countries: List<CountryEntity>) {
        val countriesString= countries.toJson()
        preferenceStorage.save(key = StorageKeyEnum.COUNTRIES_STRING, value = countriesString, type = String::class.java)
    }


}