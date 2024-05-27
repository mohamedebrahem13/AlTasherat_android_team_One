package com.solutionplus.altasherat.features.services.user.domain.repository

import com.solutionplus.altasherat.features.services.user.domain.models.User

interface IUserRepository {
    suspend fun getUserFromLocal(): User
}