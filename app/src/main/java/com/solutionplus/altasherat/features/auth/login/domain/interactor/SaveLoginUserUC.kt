package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import javax.inject.Inject

class SaveLoginUserUC @Inject constructor(
    private val repository: LoginRepository,
    private val encryptionService: IEncryptionService
) : BaseUseCase<String, LoginUserInfo>() {
    override suspend fun execute(params: LoginUserInfo?): String {
        params?.let { user ->
            val userToString = Gson().toJson(user)
            val userToByteArray = userToString.encodeToByteArray()
            val encryptedUser =
                encryptionService.encrypt(SecretKeyAliasEnum.USER_SECRET_KEY, userToByteArray)
            repository.saveUser(encryptedUser.decodeToString())
        }
        return "User Saved Successfully..."
    }
}