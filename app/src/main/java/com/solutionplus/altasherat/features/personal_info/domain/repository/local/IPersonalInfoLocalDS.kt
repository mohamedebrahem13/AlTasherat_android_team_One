package com.solutionplus.altasherat.features.personal_info.domain.repository.local

import com.solutionplus.altasherat.features.personal_info.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.personal_info.data.models.entity.UserEntity

internal interface IPersonalInfoLocalDS {
    fun getUserPersonalInfo(): UserEntity
    fun getCountries(): ArrayList<CountryEntity>
}