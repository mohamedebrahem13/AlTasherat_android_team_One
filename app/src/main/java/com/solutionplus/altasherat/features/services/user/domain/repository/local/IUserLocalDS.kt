package com.solutionplus.altasherat.features.services.user.domain.repository.local

import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity

internal interface IUserLocalDS {
    suspend fun getUser(): UserEntity
}