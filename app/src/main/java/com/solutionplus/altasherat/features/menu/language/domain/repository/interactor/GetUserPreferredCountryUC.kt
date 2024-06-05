package com.solutionplus.altasherat.features.menu.language.domain.repository.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.menu.language.domain.repository.ILanguageRepository

class GetUserPreferredCountryUC( private val languageRepository: ILanguageRepository): BaseUseCase<String, Unit>() {
    override suspend fun execute(params: Unit?): String {
        return  languageRepository.getUserPreferredCountry()
    }
}