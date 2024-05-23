package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository

class SetOnboardingShownUseCase (
    private val splashRepository: ISplashRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun execute(params: Boolean?) {
        params?.run {
            splashRepository.setOnboardingShown(params)
        }
    }
}