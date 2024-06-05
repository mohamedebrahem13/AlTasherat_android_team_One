package com.solutionplus.altasherat.features.account.personal_info.domain.repository.remote

import com.solutionplus.altasherat.features.account.personal_info.data.models.dto.UpdateInfoResponseDto
import java.io.File

internal interface IPersonalInfoRemoteDS {
    suspend fun updatePersonalInfo(
        updatePersonalInfo: Map<String, String>,
        files: Map<String, List<File>>
    ): UpdateInfoResponseDto
}