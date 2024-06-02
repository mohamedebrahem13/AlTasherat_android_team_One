package com.solutionplus.altasherat.features.services.user.data.repository.local

import com.solutionplus.altasherat.android.extentions.base64Decode
import com.solutionplus.altasherat.android.extentions.base64Encode
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
import com.solutionplus.altasherat.android.extentions.toJson
import com.solutionplus.altasherat.common.data.repository.local.StorageKeyEnum
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.IKeyValueStorageProvider
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS

internal class UserLocalDS(
    private val keyStorageProvider: IKeyValueStorageProvider,
    private val encryptionService: IEncryptionService
) : IUserLocalDS {
    override suspend fun getUser(): UserEntity {
        val encryptedUser = keyStorageProvider.get(
            key = StorageKeyEnum.USER_KEY,
            defaultValue = "",
            type = String::class.java
        )

        val decryptedUser = encryptionService.decrypt(
            keyAlias = SecretKeyAliasEnum.USER,
            data = encryptedUser.base64Decode()
        )

        return decryptedUser.decodeToString().getModelFromJSON(UserEntity::class.java)
    }

    override suspend fun saveUser(user: UserEntity) {
        val userToString = user.toJson()
        val userToByteArray = userToString.encodeToByteArray()
        val encryptedUser =
            encryptionService.encrypt(SecretKeyAliasEnum.USER, userToByteArray)
        keyStorageProvider.save(
            StorageKeyEnum.USER_KEY,
            encryptedUser.base64Encode(),
            String::class.java
        )
    }

    override suspend fun deleteUser() {
        keyStorageProvider.delete<String>(
            key = StorageKeyEnum.USER_KEY,
            type = String::class.java
        )
    }
}