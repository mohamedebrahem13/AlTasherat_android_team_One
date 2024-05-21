package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import javax.inject.Inject

class GetCountriesFromLocalUseCase (
    private val splashRepository: ISplashRepository
) : BaseUseCase<List<Country>,Unit>(){
    override suspend fun execute(params: Unit?): List<Country> {
        return splashRepository.getCountriesFromLocal()
    }
}