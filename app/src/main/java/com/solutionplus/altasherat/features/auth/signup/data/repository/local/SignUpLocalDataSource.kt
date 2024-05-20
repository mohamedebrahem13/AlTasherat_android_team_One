package com.solutionplus.altasherat.features.auth.signup.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import javax.inject.Inject

class SignUpLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
) : ISignUpLocalDataSource {
    override suspend fun saveUser(user: String) {
        localProvider.save(StorageKeyEnum.USER_KEY, user, String::class.java)
    }
}









