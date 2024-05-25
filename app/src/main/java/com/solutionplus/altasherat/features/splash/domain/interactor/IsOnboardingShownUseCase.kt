package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository

class IsOnboardingShownUseCase (
    private val splashRepository: ISplashRepository
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun execute(params: Unit?): Boolean {
        return splashRepository.isOnboardingShown()
    }
}