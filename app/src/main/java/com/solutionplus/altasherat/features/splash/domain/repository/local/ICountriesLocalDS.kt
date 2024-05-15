package com.solutionplus.altasherat.features.splash.domain.repository.local

interface ICountriesLocalDS {
    suspend fun saveCountryString(countriesString: String)

}