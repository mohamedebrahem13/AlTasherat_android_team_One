package com.solutionplus.altasherat.features.services.language.domain.interactor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.services.language.domain.repository.ILanguageRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository

class GetCachedLanguageUC(
    private val repository: ILanguageRepository
) : BaseUseCase<String, Unit>() {

    override suspend fun execute(params: Unit?): String {
        return repository.getLanguageFromLocal()
    }
}