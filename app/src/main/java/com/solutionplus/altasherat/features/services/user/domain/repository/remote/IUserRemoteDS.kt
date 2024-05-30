package com.solutionplus.altasherat.features.services.user.domain.repository.remote

import com.solutionplus.altasherat.features.services.user.data.models.dto.UserResponseDto

internal interface IUserRemoteDS {
    suspend fun getUser(): UserResponseDto
}