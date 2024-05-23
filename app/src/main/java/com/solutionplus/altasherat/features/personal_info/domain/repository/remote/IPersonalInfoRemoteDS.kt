package com.solutionplus.altasherat.features.personal_info.domain.repository.remote

import com.solutionplus.altasherat.features.personal_info.data.models.dto.UpdateInfoResponseDto
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest

internal interface IPersonalInfoRemoteDS {
    suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfoResponseDto
}