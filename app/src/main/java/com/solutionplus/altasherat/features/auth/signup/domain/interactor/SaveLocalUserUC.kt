package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import javax.inject.Inject

class SaveLocalUserUC @Inject constructor(
    private val repository: SignUpRepository,
    private val encryptionService: IEncryptionService
) : BaseUseCase<Unit, UserInfo>() {
    override suspend fun execute(params: UserInfo?) {
        params?.let { user ->
            val userToString = Gson().toJson(user)
            val userToByteArray = userToString.encodeToByteArray()
            val encryptedUser =
                encryptionService.encrypt(SecretKeyAliasEnum.USER_SECRET_KEY, userToByteArray)
            repository.saveUser(encryptedUser.decodeToString())
        }
    }
}