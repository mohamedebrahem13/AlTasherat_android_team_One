package com.solutionplus.altasherat.features.services.country.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.repository.ICountriesRepository

class GetCountriesUC(
    private val repository: ICountriesRepository
) : BaseUseCase<List<Country>, Unit>() {

    override suspend fun execute(params: Unit?): List<Country> {
        return repository.getCountriesFromRemote()
    }
}