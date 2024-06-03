package com.solutionplus.altasherat.features.account.personal_info.domain.repository

import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.account.personal_info.domain.models.UpdateInfo

interface IPersonalInfoRepository {
    suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfo
}