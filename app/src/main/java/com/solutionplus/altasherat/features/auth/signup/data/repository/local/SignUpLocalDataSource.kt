package com.solutionplus.altasherat.features.auth.signup.data.repository.local

import com.solutionplus.altasherat.android.extentions.base64Encode
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import javax.inject.Inject

internal class SignUpLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
    private val encryptionService: IEncryptionService
) : ISignUpLocalDataSource {

    override suspend fun saveUserToken(token: String) {
        val userTokenToByteArray = token.encodeToByteArray()
        val encryptedToken =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_TOKEN, userTokenToByteArray)
        localProvider.save(
            StorageKeyEnum.USER_TOKEN_KEY,
            encryptedToken.base64Encode(),
            String::class.java
        )
    }
}









