package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.home.profile.domain.repository.IProfileRepository
import javax.inject.Inject

class LogoutUC (private val repository: IProfileRepository) : BaseUseCase<String,Unit> (){
    override suspend fun execute(params: Unit?): String {
       return repository.logout()
    }
}
