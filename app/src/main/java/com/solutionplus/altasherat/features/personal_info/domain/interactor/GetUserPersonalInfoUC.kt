package com.solutionplus.altasherat.features.personal_info.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personal_info.domain.models.User
import com.solutionplus.altasherat.features.personal_info.domain.repository.IPersonalInfoRepository

class GetUserPersonalInfoUC(
    private val repository: IPersonalInfoRepository
) : BaseUseCase<User, Unit>() {

    override suspend fun execute(params: Unit?): User {
        return repository.getUserPersonalInfo()
    }
}