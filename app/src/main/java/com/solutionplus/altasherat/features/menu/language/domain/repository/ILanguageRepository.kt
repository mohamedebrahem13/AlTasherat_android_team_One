package com.solutionplus.altasherat.features.menu.language.domain.repository

interface ILanguageRepository {
    suspend fun saveUserPreferredLanguage(language: String)
    suspend fun getUserPreferredCountry(): String
    suspend fun saveUserPreferredCountry(country: String)
}