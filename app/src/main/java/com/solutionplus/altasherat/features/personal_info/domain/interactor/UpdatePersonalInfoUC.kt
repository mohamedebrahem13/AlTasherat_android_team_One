package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.models.UpdateInfo
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository

class UpdatePersonalInfoUC(
    private val repository: IPersonalInfoRepository
) : BaseUseCase<UpdateInfo, UpdateInfoRequest>() {

    override suspend fun execute(params: UpdateInfoRequest?): UpdateInfo {
        return repository.updatePersonalInfo(params!!)
    }
}