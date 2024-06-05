package com.solutionplus.altasherat.features.services.language.domain.repository.local

internal interface ILanguageLocalDS {
    suspend fun getLanguage(): String
}