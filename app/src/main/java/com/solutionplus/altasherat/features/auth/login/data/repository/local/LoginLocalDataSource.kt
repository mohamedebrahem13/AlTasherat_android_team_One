package com.solutionplus.altasherat.features.auth.login.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
) : ILoginLocalDataSource {
    override suspend fun saveUser(user: String) {
        localProvider.save(StorageKeyEnum.USER_KEY, user, String::class.java)
    }

    override suspend fun saveToken(token: String) {
        localProvider.save(StorageKeyEnum.USER_TOKEN_KEY, token, String::class.java)
    }
}
