package com.solutionplus.altasherat.features.splash.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.splash.domain.repository.ISplashRepository

class GetUserPreferredCountryUC (private val splashRepository: ISplashRepository): BaseUseCase<String, Unit>() {
    override suspend fun execute(params: Unit?): String {
      return  splashRepository.getUserPreferredCountry()
    }
}