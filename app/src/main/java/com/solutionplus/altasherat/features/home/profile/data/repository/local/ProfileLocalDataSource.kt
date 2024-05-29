package com.solutionplus.altasherat.features.home.profile.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.home.profile.domain.repository.local.IProfileLocalDataSource
import javax.inject.Inject

internal class ProfileLocalDataSource (private val localProvider: IKeyValueStorageProvider):IProfileLocalDataSource {
    override suspend fun deleteUserInfo() {
        localProvider.delete<String>(StorageKeyEnum.USER_KEY, String::class.java)
    }

    override suspend fun deleteUserToken() {
        localProvider.delete<String>(StorageKeyEnum.USER_TOKEN_KEY, String::class.java)
    }
}