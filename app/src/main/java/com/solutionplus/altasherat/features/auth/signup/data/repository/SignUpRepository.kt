package com.solutionplus.altasherat.features.auth.signup.data.repository

import com.solutionplus.altasherat.features.auth.signup.data.mapper.SignUpMapper
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import com.solutionplus.altasherat.features.auth.signup.domain.repository.ISignUpRepository
import com.solutionplus.altasherat.features.auth.signup.domain.repository.local.ISignUpLocalDataSource
import com.solutionplus.altasherat.features.auth.signup.domain.repository.remote.ISignUpRemoteDataSource
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val signUpRemoteDataSource: ISignUpRemoteDataSource,
    private val signUpLocalDataSource: ISignUpLocalDataSource
): ISignUpRepository {

    override suspend fun signup(userRequest: UserRequest): UserInfo {
        val user = signUpRemoteDataSource.signup(userRequest)
        return SignUpMapper.dtoToDomain(user)
    }

    override suspend fun saveUser(user: String) {
        signUpLocalDataSource.saveUser(user)
    }
}











