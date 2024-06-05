package com.solutionplus.altasherat.features.menu.language.domain.repository.local

internal interface ILanguageLocalDS {
    suspend fun saveUserPreferredLanguage(language: String)
    suspend fun saveUserPreferredCountry(country: String)
    suspend fun getUserPreferredCountry(): String
}