package com.solutionplus.altasherat.features.auth.login.data.repository

import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginUserMapper
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource,
    private val loginLocalDataSource: ILoginLocalDataSource,
): ILoginRepository {

    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserResponse {
        val user = loginRemoteDataSource.loginWithPhone(userLoginRequest)
        return LoginMapper.dtoToDomain(user)
    }

    override suspend fun saveUser(user: LoginUserInfo) {
        val userEntity = LoginUserMapper.domainToEntity(user)
        loginLocalDataSource.saveUser(userEntity)
    }

    override suspend fun saveUserToken(token: String) {
        loginLocalDataSource.saveToken(token)
    }
}