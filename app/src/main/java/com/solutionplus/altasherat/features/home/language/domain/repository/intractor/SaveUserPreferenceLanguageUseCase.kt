package com.solutionplus.altasherat.features.home.language.domain.repository.intractor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.home.language.domain.repository.ILanguageRepository

class SaveUserPreferenceLanguageUseCase (
    private val languageRepository: ILanguageRepository
) : BaseUseCase<Unit, String>() {

    override suspend fun execute(params: String?) {
        params?.run {
            languageRepository.saveUserPreferredLanguage(params)
        }
    }
}