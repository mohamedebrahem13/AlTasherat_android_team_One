package com.solutionplus.altasherat.features.auth.signup.data.repository

import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.repository.local.encryption.SecretKeyAliasEnum
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionService
import com.solutionplus.altasherat.features.auth.signup.data.mapper.SignUpMapper
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val signUpRemoteDataSource: ISignUpRemoteDataSource,
    private val signUpLocalDataSource: ISignUpLocalDataSource,
    private val encryptionService: IEncryptionService
) : ISignUpRepository {

    override suspend fun signup(userSignUpRequest: UserSignUpRequest): UserInfo {
        val user = signUpRemoteDataSource.signup(userSignUpRequest)
        return SignUpMapper.dtoToDomain(user)
    }

    override suspend fun saveUser(userInfo: UserInfo) {
        val userToString = Gson().toJson(userInfo)
        val userToByteArray = userToString.encodeToByteArray()
        val encryptedUser =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_SECRET_KEY, userToByteArray)
        signUpLocalDataSource.saveUser(encryptedUser.decodeToString())
    }

    override suspend fun saveUserToken(userInfo: UserInfo) {
        val userTokenToString = userInfo.token
        val userTokenToByteArray = userTokenToString.encodeToByteArray()
        val encryptedToken =
            encryptionService.encrypt(SecretKeyAliasEnum.USER_TOKEN_SECRET_KEY, userTokenToByteArray)
        signUpLocalDataSource.saveUserToken(encryptedToken.decodeToString())
    }
}











