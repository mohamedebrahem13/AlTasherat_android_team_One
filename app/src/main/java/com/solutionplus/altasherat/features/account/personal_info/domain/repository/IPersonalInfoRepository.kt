package com.solutionplus.altasherat.features.account.personal_info.domain.repository

import com.solutionplus.altasherat.features.account.personal_info.domain.models.UpdateInfo
import java.io.File

interface IPersonalInfoRepository {
    suspend fun updatePersonalInfo(
        updatePersonalInfo: Map<String, String>,
        files: Map<String, List<File>>
    ): UpdateInfo
}