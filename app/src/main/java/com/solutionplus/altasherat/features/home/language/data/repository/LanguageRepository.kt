package com.solutionplus.altasherat.features.home.language.data.repository

import com.solutionplus.altasherat.features.home.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.home.language.domain.repository.local.ILanguageLocalDS

internal class LanguageRepository (private val localDataSource: ILanguageLocalDS): ILanguageRepository {
    override suspend fun saveUserPreferredLanguage(language: String) {
        localDataSource.saveUserPreferredLanguage(language)
    }
}