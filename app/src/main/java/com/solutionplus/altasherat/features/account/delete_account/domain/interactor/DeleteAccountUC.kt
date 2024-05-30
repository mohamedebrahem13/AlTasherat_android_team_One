package com.solutionplus.altasherat.features.account.delete_account.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.delete_account.domain.repository.IDeleteAccountRepository
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest

class DeleteAccountUC(
    private val repository: IDeleteAccountRepository
) : BaseUseCase<Unit, String>() {

    override suspend fun execute(params: String?) {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                message = "Request is null"
            )
        }
        repository.deleteAccount(params)
    }
}