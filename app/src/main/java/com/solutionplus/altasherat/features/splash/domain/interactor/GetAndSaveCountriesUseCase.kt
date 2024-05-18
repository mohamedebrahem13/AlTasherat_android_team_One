package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAndSaveCountriesUseCase @Inject constructor(
    private val splashRepository: ISplashRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {
        val countriesResponse = splashRepository.getCountries()
        splashRepository.saveCountries(countriesResponse.countries)
    }
    fun executeAndEmitState(): Flow<Resource<Unit>> = invoke()
}
