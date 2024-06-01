package com.solutionplus.altasherat.features.services.user.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class DeleteCachedUserUC(
    private val repository: IUserRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(params: Unit?) {
        return repository.deleteUser()
    }
}