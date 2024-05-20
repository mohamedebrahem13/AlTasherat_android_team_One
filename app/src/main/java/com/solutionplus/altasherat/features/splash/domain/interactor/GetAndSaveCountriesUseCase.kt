package com.solutionplus.altasherat.features.splash.domain.interactor
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository
class GetAndSaveCountriesUseCase(
    private val splashRepository: ISplashRepository
) : BaseUseCase<Unit, String>() {
    override suspend fun execute(params: String?) {
        params?.run {
            val countriesResponse = splashRepository.getCountriesFromRemote(params)
            splashRepository.saveCountries(countriesResponse.countries)
        }

    }
}
