package com.solutionplus.altasherat.features.auth.login.data.repository

import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserResponse
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.local.ILoginLocalDataSource
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

internal class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource,
    private val loginLocalDataSource: ILoginLocalDataSource,
): ILoginRepository {

    override suspend fun loginWithPhone(userLoginRequest: UserLoginRequest): LoginUserResponse {
        val result = loginRemoteDataSource.loginWithPhone(userLoginRequest)
        return LoginMapper.dtoToDomain(result)
    }

    override suspend fun saveUserToken(token: String) {
        loginLocalDataSource.saveToken(token)
    }
}