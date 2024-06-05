package com.solutionplus.altasherat.features.account.personal_info.data.repository

import com.solutionplus.altasherat.features.account.personal_info.data.mappers.UpdateInfoMapper
import com.solutionplus.altasherat.features.account.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.account.personal_info.domain.repository.remote.IPersonalInfoRemoteDS
import java.io.File

internal class PersonalInfoRepository(
    private val remoteDS: IPersonalInfoRemoteDS,
) : IPersonalInfoRepository {

    override suspend fun updatePersonalInfo(
        updatePersonalInfo: Map<String, String>,
        files: Map<String, List<File>>
    ): UpdateInfo {
        val result = remoteDS.updatePersonalInfo(updatePersonalInfo, files)
        return UpdateInfoMapper.dtoToDomain(result)
    }
}