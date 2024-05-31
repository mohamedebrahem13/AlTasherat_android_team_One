package com.solutionplus.altasherat.features.services.token.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.token.domain.repository.ITokenRepository

class DeleteCachedTokenUC(
    private val repository: ITokenRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(params: Unit?) {
        repository.deleteToken()
    }
}