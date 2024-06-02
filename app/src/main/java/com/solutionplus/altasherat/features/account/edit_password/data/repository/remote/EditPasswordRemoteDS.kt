package com.solutionplus.altasherat.features.account.edit_password.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.remote.IEditPasswordRemoteDS

internal class EditPasswordRemoteDS(
    private val networkProvider: INetworkProvider
) : IEditPasswordRemoteDS {

    override suspend fun editPassword(request: EditPasswordRequest): com.solutionplus.altasherat.features.account.edit_password.data.models.dto.EditPasswordResponseDto {
        return networkProvider.post(
            pathUrl = "change-password",
            headers = hashMapOf("Authorization-Required" to "true"),
            requestBody = request,
            responseWrappedModel = com.solutionplus.altasherat.features.account.edit_password.data.models.dto.EditPasswordResponseDto::class.java
        )
    }
}