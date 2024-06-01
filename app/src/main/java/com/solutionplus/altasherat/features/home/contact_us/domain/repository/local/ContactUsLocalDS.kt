package com.solutionplus.altasherat.features.home.contact_us.domain.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.home.contact_us.data.repository.local.IContactUsLocalDataSource

internal class ContactUsLocalDS(private val localProvider: IKeyValueStorageProvider):IContactUsLocalDataSource {
    override suspend fun hasTokenKey(): Boolean {
        return localProvider.hasKey<String>(StorageKeyEnum.USER_TOKEN_KEY, String::class.java)
    }
}