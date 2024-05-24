package com.solutionplus.altasherat.features.auth.login.data.repository

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource,
    private val loginLocalDataSource: ILoginLocalDataSource,
    private val encryptionService: IEncryptionService
): ILoginRepository {

    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserInfo {
        val user = loginRemoteDataSource.loginWithPhone(userLoginRequest)
        return LoginMapper.dtoToDomain(user)
    }

    override suspend fun saveUser(user: LoginUserInfo) {
        val userToString = Gson().toJson(user)
        val userToByteArray = userToString.encodeToByteArray()
        val encryptedUser =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_SECRET_KEY, userToByteArray)
        loginLocalDataSource.saveUser(encryptedUser.decodeToString())
    }

    override suspend fun saveUserToken(token: LoginUserInfo) {
        val userTokenToString = token.token
        val userTokenToByteArray = userTokenToString.encodeToByteArray()
        val encryptedToken =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_TOKEN_SECRET_KEY, userTokenToByteArray)
        loginLocalDataSource.saveToken(encryptedToken.decodeToString())
    }
}