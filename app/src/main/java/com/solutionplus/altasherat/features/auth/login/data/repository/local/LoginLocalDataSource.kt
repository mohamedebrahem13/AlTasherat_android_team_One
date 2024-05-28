package com.solutionplus.altasherat.features.auth.login.data.repository.local

import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
    private val encryptionService: IEncryptionService
) : ILoginLocalDataSource {

    override suspend fun saveToken(token: String) {
        val userTokenToByteArray = token.encodeToByteArray()
        val encryptedToken =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_TOKEN, userTokenToByteArray)
        localProvider.save(
            StorageKeyEnum.USER_TOKEN_KEY,
            encryptedToken.decodeToString(),
            String::class.java
        )
    }
}
