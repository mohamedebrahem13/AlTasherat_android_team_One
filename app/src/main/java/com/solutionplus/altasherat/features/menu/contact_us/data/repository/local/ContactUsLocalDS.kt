package com.solutionplus.altasherat.features.menu.contact_us.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.menu.contact_us.domain.repository.local.IContactDataSource

internal class ContactUsLocalDS(private val localProvider: IKeyValueStorageProvider):
    IContactDataSource {
    override suspend fun hasTokenKey(): Boolean {
        return localProvider.hasKey<String>(StorageKeyEnum.USER_TOKEN_KEY, String::class.java)
    }
}