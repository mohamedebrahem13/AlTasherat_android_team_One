package com.solutionplus.altasherat.features.edit_password.domain.repository.remote

import com.solutionplus.altasherat.features.edit_password.data.models.dto.EditPasswordResponseDto
import com.solutionplus.altasherat.features.edit_password.data.models.request.EditPasswordRequest

internal interface IEditPasswordRemoteDS {
    suspend fun editPassword(request: EditPasswordRequest): EditPasswordResponseDto
}