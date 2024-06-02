package com.solutionplus.altasherat.features.home.language.domain.repository

interface ILanguageRepository {
    suspend fun saveUserPreferredLanguage(language: String)
}