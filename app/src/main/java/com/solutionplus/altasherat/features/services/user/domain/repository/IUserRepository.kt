package com.solutionplus.altasherat.features.services.user.domain.repository

import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUserRepository {
    suspend fun getUserFromLocal(): User
    suspend fun getUserFromRemote(): User
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
}