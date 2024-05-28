package com.solutionplus.altasherat.features.services.user.data.repository

import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS

internal class UserRepository(
    private val localDS: IUserLocalDS,
) : IUserRepository {
    override suspend fun getUserFromLocal(): User {
        val user = localDS.getUser()
        return UserMapper.entityToDomain(user)
    }

    override suspend fun saveUser(user: User) {
        val userEntity = UserMapper.domainToEntity(user)
        localDS.saveUser(userEntity)
    }
}