package com.solutionplus.altasherat.features.services.token.data.repository.local

import com.solutionplus.altasherat.android.extentions.base64Decode
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.services.token.domain.repository.local.ITokenLocalDS

internal class TokenLocalDS(
    private val keyValueStorage: IKeyValueStorageProvider,
    private val encryptionService: IEncryptionService
) : ITokenLocalDS {

    override suspend fun getToken(): ByteArray {
        val encryptedToken = keyValueStorage.get(
            key = StorageKeyEnum.USER_TOKEN_KEY,
            defaultValue = "",
            type = String::class.java
        )

        val decryptedToken = encryptionService.decrypt(
            keyAlias = SecretKeyAliasEnum.USER_TOKEN,
            data = encryptedToken.base64Decode()
        )
        return decryptedToken
    }

    override suspend fun deleteToken() {
        keyValueStorage.delete<String>(
            key = StorageKeyEnum.USER_TOKEN_KEY,
            type = String::class.java
        )
    }
}