package com.solutionplus.altasherat.features.menu.language.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.menu.language.domain.repository.local.ILanguageLocalDS

internal class LanguageLocalDS(private val preferenceStorage: IKeyValueStorageProvider):
    ILanguageLocalDS {
    override suspend fun saveUserPreferredLanguage(language: String) {
        preferenceStorage.save(key = StorageKeyEnum.LANGUAGE, value = language, type = String::class.java)
    }
}