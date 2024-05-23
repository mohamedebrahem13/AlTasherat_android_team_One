package com.solutionplus.altasherat.features.personal_info.domain.repository

import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.personal_info.domain.models.User

interface IPersonalInfoRepository {
    suspend fun getUserPersonalInfo(): User
    suspend fun getCountries(): ArrayList<Country>
    suspend fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest): UpdateInfo
}