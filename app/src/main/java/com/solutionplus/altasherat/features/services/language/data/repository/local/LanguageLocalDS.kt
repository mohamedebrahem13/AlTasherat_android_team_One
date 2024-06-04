package com.solutionplus.altasherat.features.services.language.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.services.language.domain.repository.local.ILanguageLocalDS

internal class LanguageLocalDS(
    private val keyStorageProvider: IKeyValueStorageProvider,
) : ILanguageLocalDS {

    override suspend fun getLanguage(): String {
        return keyStorageProvider.get(
            key = StorageKeyEnum.LANGUAGE,
            defaultValue = "",
            type = String::class.java
        )
    }
}