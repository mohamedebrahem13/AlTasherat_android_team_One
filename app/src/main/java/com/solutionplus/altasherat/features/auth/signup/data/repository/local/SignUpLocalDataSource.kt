package com.solutionplus.altasherat.features.auth.signup.data.repository.local

import com.solutionplus.altasherat.android.extentions.toJson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.data.model.entity.SignUpUserEntity
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import javax.inject.Inject

class SignUpLocalDataSource @Inject constructor(
    private val localProvider: IKeyValueStorageProvider,
    private val encryptionService: IEncryptionService
) : ISignUpLocalDataSource {
    override suspend fun saveUser(user: SignUpUserEntity) {
        val userToString = user.toJson()
        val userToByteArray = userToString.encodeToByteArray()
        val encryptedUser =
            encryptionService.encrypt(SecretKeyAliasEnum.USER, userToByteArray)
        localProvider.save(
            StorageKeyEnum.USER_KEY,
            encryptedUser.decodeToString(),
            String::class.java
        )
    }

    override suspend fun saveUserToken(token: String) {
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









