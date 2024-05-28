package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import javax.inject.Inject

class DeleteUserInfoAndTokenUC @Inject constructor(private val repository: IProfileRepository) :
    BaseUseCase<Unit, Unit>() {

    override suspend fun execute(params: Unit?) {
        repository.deleteUserInfo()
        repository.deleteUserToken()
    }
}