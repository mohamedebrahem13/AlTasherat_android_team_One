package com.solutionplus.altasherat.features.services.user.data.repository.local

import android.util.Base64
import com.solutionplus.altasherat.android.extentions.getModelFromJSON
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
            data = Base64.decode(encryptedUser, Base64.NO_WRAP)
        )

        return decryptedUser.decodeToString().getModelFromJSON(UserEntity::class.java)
    }
}