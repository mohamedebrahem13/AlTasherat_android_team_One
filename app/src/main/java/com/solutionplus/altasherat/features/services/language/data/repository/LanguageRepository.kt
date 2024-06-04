package com.solutionplus.altasherat.features.services.language.data.repository

import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS

internal class LanguageRepository(
    private val localDS: ILanguageLocalDS,
) : ILanguageRepository {

    override suspend fun getLanguageFromLocal(): String {
        return localDS.getLanguage()
    }
}