package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class GetAndSaveCountriesUC(
        private val countriesRepository: ICountriesRepository
    ) : BaseUseCase<Unit, String>() {
        override suspend fun execute(params: String?) {
            params?.run {
                val countriesResponse = countriesRepository.getCountriesFromRemote(params)
                countriesRepository.saveCountries(countriesResponse)
            }

        }
    }
