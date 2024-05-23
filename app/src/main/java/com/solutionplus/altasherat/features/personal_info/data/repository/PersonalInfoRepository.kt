package com.solutionplus.altasherat.features.personal_info.data.repository

import com.solutionplus.altasherat.features.personal_info.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.personal_info.data.mappers.UpdateInfoMapper
import com.solutionplus.altasherat.features.personal_info.data.mappers.UserMapper
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.local.IPersonalInfoLocalDS
import com.solutionplus.altasherat.features.personal_info.domain.repository.remote.IPersonalInfoRemoteDS

internal class PersonalInfoRepository(
    private val remoteDS: IPersonalInfoRemoteDS,
    private val localDS: IPersonalInfoLocalDS
) : IPersonalInfoRepository {

    override suspend fun getUserPersonalInfo(): User {
        val user = localDS.getUserPersonalInfo()
        return UserMapper.entityToDomain(user)
    }

    override suspend fun getCountries(): ArrayList<Country> {
        val countries = localDS.getCountries()
        return countries.map { CountryMapper.entityToDomain(it) } as ArrayList<Country>
    }

    override suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfo {
        val result = remoteDS.updatePersonalInfo(updateInfoRequest)
        return UpdateInfoMapper.dtoToDomain(result)
    }
}