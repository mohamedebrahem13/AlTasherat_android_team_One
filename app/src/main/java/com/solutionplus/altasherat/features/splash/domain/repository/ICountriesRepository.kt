package com.solutionplus.altasherat.features.splash.domain.repository

import com.solutionplus.altasherat.features.splash.domain.models.CountriesResponse
import com.solutionplus.altasherat.features.splash.domain.models.Country

interface ICountriesRepository {
        suspend fun getCountries() : CountriesResponse// Change the return type as needed
        suspend fun saveCountries(countries: List<Country>)
}