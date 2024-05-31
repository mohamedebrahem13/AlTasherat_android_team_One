package com.solutionplus.altasherat.features.home.language.domain.repository.local

internal interface ILanguageLocalDS {
    suspend fun saveUserPreferredLanguage(language: String)
}