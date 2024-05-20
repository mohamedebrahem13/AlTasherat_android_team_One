package com.solutionplus.altasherat.features.auth.login.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
) : ILoginLocalDataSource {
    override suspend fun saveUserToken(token: String) {
        localProvider.update(StorageKeyEnum.USER_KEY, token, String::class.java)
    }
}
