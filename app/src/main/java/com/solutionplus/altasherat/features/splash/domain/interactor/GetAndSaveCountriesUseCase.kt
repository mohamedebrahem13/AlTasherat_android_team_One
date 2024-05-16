package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ICountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAndSaveCountriesUseCase @Inject constructor(
    private val countriesRepository: ICountriesRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {
        val countriesResponse = countriesRepository.getCountries()
        countriesRepository.saveCountries(countriesResponse.countries)
    }
    fun executeAndEmitState(): Flow<Resource<Unit>> = invoke()
}
