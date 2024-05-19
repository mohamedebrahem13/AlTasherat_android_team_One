package com.solutionplus.altasherat.features.personal_info.data.repository

import com.solutionplus.altasherat.features.personal_info.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.personal_info.data.mappers.UserMapper
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository
import com.solutionplus.altasherat.features.personal_info.domain.repository.local.IPersonalInfoLocalDS

internal class PersonalInfoRepository(
    private val localDS: IPersonalInfoLocalDS
) : IPersonalInfoRepository {
    override fun getUserPersonalInfo(): User {
        val user = localDS.getUserPersonalInfo()
        return UserMapper.entityToDomain(user)
    }

    override fun getCountries(): ArrayList<Country> {
        val countries = localDS.getCountries()
        return countries.map { CountryMapper.entityToDomain(it) } as ArrayList<Country>
    }
}