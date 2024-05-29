package com.solutionplus.altasherat.features.personal_info.data.repository

import com.solutionplus.altasherat.features.personal_info.data.mappers.UpdateInfoMapper
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.remote.IPersonalInfoRemoteDS

internal class PersonalInfoRepository(
    private val remoteDS: IPersonalInfoRemoteDS,
) : IPersonalInfoRepository {

    override suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfo {
        val result = remoteDS.updatePersonalInfo(updateInfoRequest)
        return UpdateInfoMapper.dtoToDomain(result)
    }
}