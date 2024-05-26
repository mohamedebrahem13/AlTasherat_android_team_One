package com.solutionplus.altasherat.features.auth.signup.data.repository

import com.solutionplus.altasherat.features.auth.signup.data.mapper.SignUpMapper
import com.solutionplus.altasherat.features.auth.signup.data.mapper.UserMapper
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserResponse
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val signUpRemoteDataSource: ISignUpRemoteDataSource,
    private val signUpLocalDataSource: ISignUpLocalDataSource,
) : ISignUpRepository {

    override suspend fun signup(userSignUpRequest: UserSignUpRequest): UserResponse {
        val user = signUpRemoteDataSource.signup(userSignUpRequest)
        return SignUpMapper.dtoToDomain(user)
    }

    override suspend fun saveUser(userInfo: UserInfo) {
        val userEntity = UserMapper.domainToEntity(userInfo)
        signUpLocalDataSource.saveUser(userEntity)
    }

    override suspend fun saveUserToken(token: String) {
        signUpLocalDataSource.saveUserToken(token)
    }
}











