package com.solutionplus.altasherat.features.menu.language.domain.repository.local

internal interface ILanguageLocalDS {
    suspend fun saveUserPreferredLanguage(language: String)
}