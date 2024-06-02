package com.solutionplus.altasherat.features.menu.language.domain.repository.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.menu.language.domain.repository.ILanguageRepository

class SaveUserPreferenceLanguageUseCase (
    private val languageRepository: ILanguageRepository
) : BaseUseCase<Unit, String>() {

    override suspend fun execute(params: String?) {
        params?.run {
            languageRepository.saveUserPreferredLanguage(params)
        }
    }
}