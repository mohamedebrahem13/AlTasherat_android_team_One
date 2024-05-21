package com.solutionplus.altasherat.features.auth.login.domain.interactor

import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.login.data.repository.LoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import javax.inject.Inject

class SaveLoginTokenUC @Inject constructor(
    private val repository: LoginRepository,
    private val encryptionService: IEncryptionService
) : BaseUseCase<String, LoginUserInfo>() {
    override suspend fun execute(params: LoginUserInfo?): String {
        params?.let { user ->
            val userTokenToString = user.token
            val userTokenToByteArray = userTokenToString.encodeToByteArray()
            val encryptedToken =
                encryptionService.encrypt(SecretKeyAliasEnum.USER_TOKEN_SECRET_KEY, userTokenToByteArray)
            repository.saveUserToken(encryptedToken.decodeToString())
        }
        return "Token Saved Successfully..."
    }
}