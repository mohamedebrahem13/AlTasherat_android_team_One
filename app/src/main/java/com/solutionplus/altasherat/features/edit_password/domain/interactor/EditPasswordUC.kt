package com.solutionplus.altasherat.features.edit_password.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.edit_password.domain.repository.IEditPasswordRepository
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest

class EditPasswordUC(
    private val repository: IEditPasswordRepository
) : BaseUseCase<Unit, EditPasswordRequest>() {

    override suspend fun execute(params: EditPasswordRequest?) {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = UpdateInfoRequest::class,
                message = "Request is null"
            )
        }

        repository.editPassword(params)
    }
}