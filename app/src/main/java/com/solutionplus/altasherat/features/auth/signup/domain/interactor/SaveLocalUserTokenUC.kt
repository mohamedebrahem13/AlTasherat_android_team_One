package com.solutionplus.altasherat.features.auth.signup.domain.interactor

import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.data.repository.SignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import javax.inject.Inject

class SaveLocalUserTokenUC @Inject constructor(
    private val repository: SignUpRepository,
    private val encryptionService: IEncryptionService
) : BaseUseCase<String, UserInfo>() {
    override suspend fun execute(params: UserInfo?): String {
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