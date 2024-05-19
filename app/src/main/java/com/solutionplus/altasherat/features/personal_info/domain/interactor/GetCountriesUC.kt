package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository

class GetCountriesUC(
    private val repository: IPersonalInfoRepository
) : BaseUseCase<ArrayList<Country>, Unit>() {

    override suspend fun execute(params: Unit?): ArrayList<Country> {
        return repository.getCountries()
    }
}