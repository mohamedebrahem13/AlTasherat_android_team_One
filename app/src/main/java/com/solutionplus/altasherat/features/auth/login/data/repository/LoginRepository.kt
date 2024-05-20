package com.solutionplus.altasherat.features.auth.login.data.repository

import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource,
    private val loginLocalDataSource: ILoginLocalDataSource
): ILoginRepository {

    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserInfo {
        val user = loginRemoteDataSource.loginWithPhone(userLoginRequest)
        return LoginMapper.dtoToDomain(user)
    }

    override suspend fun saveUserToken(token: String) {
        loginLocalDataSource.saveUserToken(token)
    }
}