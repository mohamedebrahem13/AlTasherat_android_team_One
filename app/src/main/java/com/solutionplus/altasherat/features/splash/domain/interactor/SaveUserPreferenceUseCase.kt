package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository

class SaveUserPreferenceUseCase(
    private val splashRepository: ISplashRepository
) : BaseUseCase<Unit, UserPreference>() {

    override suspend fun execute(params: UserPreference?) {
        params?.run {
            splashRepository.saveUserPreferredLanguage(params.preferredLanguage)
            splashRepository.saveUserPreferredCountry(params.preferredCountry)
        }
    }
}