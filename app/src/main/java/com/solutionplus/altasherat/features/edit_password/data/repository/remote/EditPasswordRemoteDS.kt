package com.solutionplus.altasherat.features.edit_password.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.edit_password.data.models.dto.EditPasswordResponseDto
import com.solutionplus.altasherat.features.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.edit_password.domain.repository.remote.IEditPasswordRemoteDS

internal class EditPasswordRemoteDS(
    private val networkProvider: INetworkProvider
) : IEditPasswordRemoteDS {

    override suspend fun editPassword(request: EditPasswordRequest): EditPasswordResponseDto {
        return networkProvider.post(
            pathUrl = "change-password",
            headers = hashMapOf("Authorization-Required" to "true"),
            requestBody = request,
            responseWrappedModel = EditPasswordResponseDto::class.java
        )
    }
}