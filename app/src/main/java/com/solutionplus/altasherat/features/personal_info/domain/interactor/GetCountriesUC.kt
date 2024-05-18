package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository

class GetCountriesUC(
    private val repository: IPersonalInfoRepository
) : BaseUseCase<List<Country>, Unit>() {

    override suspend fun execute(params: Unit?): List<Country> {
        return repository.getCountries()
    }
}