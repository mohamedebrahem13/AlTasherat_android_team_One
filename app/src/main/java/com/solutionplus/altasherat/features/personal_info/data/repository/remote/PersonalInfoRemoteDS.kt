package com.solutionplus.altasherat.features.personal_info.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.repository.remote.IPersonalInfoRemoteDS

internal class PersonalInfoRemoteDS(
    private val networkProvider: INetworkProvider
) : IPersonalInfoRemoteDS {

    override suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfoResponseDto {
        return networkProvider.post(
            pathUrl = "update-account",
            requestBody = updateInfoRequest,
            headers = hashMapOf("accept" to "application/json"),
            responseWrappedModel = UpdateInfoResponseDto::class.java
        )
    }
}