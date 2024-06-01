package com.solutionplus.altasherat.features.home.contact_us.domain.repository.intractor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.IContactUsRepository

class CheckTokenKeyUC (
    private val repository: IContactUsRepository
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(params: Unit?): Boolean {
        return repository.hasTokenKey()
    }
}