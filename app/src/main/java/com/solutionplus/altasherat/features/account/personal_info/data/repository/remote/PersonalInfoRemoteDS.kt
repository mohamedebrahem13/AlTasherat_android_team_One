package com.solutionplus.altasherat.features.account.personal_info.data.repository.remote

import com.solutionplus.altasherat.common.domain.repository.remote.INetworkProvider
import com.solutionplus.altasherat.features.account.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.remote.IPersonalInfoRemoteDS
import java.io.File

internal class PersonalInfoRemoteDS(
    private val networkProvider: INetworkProvider
) : IPersonalInfoRemoteDS {

    override suspend fun updatePersonalInfo(
        updatePersonalInfo: Map<String, String>,
        files: Map<String, List<File>>
    ): UpdateInfoResponseDto {
        return networkProvider.postWithFile(
            pathUrl = "update-account",
            requestBody = updatePersonalInfo,
            files = files,
            headers = hashMapOf(
                "Authorization-Required" to "true",
            ),
            responseWrappedModel = UpdateInfoResponseDto::class.java
        )
    }
}