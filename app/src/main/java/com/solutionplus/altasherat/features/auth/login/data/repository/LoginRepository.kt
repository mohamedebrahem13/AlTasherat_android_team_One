package com.solutionplus.altasherat.features.auth.login.data.repository

import com.solutionplus.altasherat.features.auth.login.data.mapper.LoginMapper
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import com.solutionplus.altasherat.features.auth.login.domain.repository.ILoginRepository
import com.solutionplus.altasherat.features.auth.login.domain.repository.remote.ILoginRemoteDataSource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource
): ILoginRepository {

    override suspend fun loginWithPhone(loginRequest: LoginRequest): LoginUserInfo {
        val user = loginRemoteDataSource.loginWithPhone(loginRequest)
        return LoginMapper.dtoToDomain(user)
    }
}