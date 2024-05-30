package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository

class CheckTokenKeyUC (
    private val repository: IProfileRepository
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return repository.hasTokenKey()
    }
}