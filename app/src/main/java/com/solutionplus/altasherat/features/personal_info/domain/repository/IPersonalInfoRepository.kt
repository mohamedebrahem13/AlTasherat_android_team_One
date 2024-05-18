package com.solutionplus.altasherat.features.personal_info.domain.repository

import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.User

interface IPersonalInfoRepository {
    fun getUserPersonalInfo(): User
    fun getCountries(): List<Country>
}