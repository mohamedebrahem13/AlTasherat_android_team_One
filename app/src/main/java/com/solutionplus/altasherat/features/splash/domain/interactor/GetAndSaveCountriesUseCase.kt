package com.solutionplus.altasherat.features.splash.domain.interactor
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
import javax.inject.Inject

class GetAndSaveCountriesUseCase @Inject constructor(
    private val splashRepository: ISplashRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params: Unit?) {
        val countriesResponse = splashRepository.getCountries()
        splashRepository.saveCountries(countriesResponse.countries)
    }
}
