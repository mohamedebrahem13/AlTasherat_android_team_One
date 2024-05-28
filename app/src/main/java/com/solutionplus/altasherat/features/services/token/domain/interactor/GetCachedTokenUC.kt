package com.solutionplus.altasherat.features.services.token.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.token.domain.repository.ITokenRepository

class GetCachedTokenUC(
    private val repository: ITokenRepository
) : BaseUseCase<ByteArray, Unit>() {

    override suspend fun execute(params: Unit?): ByteArray {
        return repository.getTokenFromLocal()
    }
}