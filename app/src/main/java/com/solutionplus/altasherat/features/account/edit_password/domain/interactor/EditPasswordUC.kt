package com.solutionplus.altasherat.features.account.edit_password.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.IEditPasswordRepository

class EditPasswordUC(
    private val repository: IEditPasswordRepository
) : BaseUseCase<Unit, EditPasswordRequest>() {

    override suspend fun execute(params: EditPasswordRequest?) {
        requireNotNull(params) {
            throw AlTasheratException.Local.RequestValidation(
                clazz = EditPasswordRequest::class,
                message = "Request is null"
            )
        }
        validateRequest(params)?.let { message ->
            throw AlTasheratException.Local.RequestValidation(
                clazz = EditPasswordRequest::class,
                message = message
            )
        }


        repository.editPassword(params)
    }

    private fun validateRequest(request: EditPasswordRequest): String? {
        return request.run {
            when {
                !isOldPasswordValid() -> "Old password is not valid"
                !isNewPasswordValid() -> "New password is not valid"
                !isConfirmPasswordValid() -> "Confirm password is not valid"
                else -> null
            }
        }
    }
}