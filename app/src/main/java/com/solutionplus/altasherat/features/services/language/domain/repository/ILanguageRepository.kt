package com.solutionplus.altasherat.features.services.language.domain.repository

interface ILanguageRepository {
    suspend fun getLanguageFromLocal(): String
}