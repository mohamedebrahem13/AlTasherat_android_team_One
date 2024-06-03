package com.solutionplus.altasherat.features.account.edit_password.domain.interactor

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.CONFIRM_PASSWORD_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD_CONFIRMATION
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD_VALIDATION
import com.solutionplus.altasherat.common.domain.constants.Constants.OLD_PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.PASSWORD_VALIDATION
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

        validateRequest(params).takeIf { it.isNotEmpty() }?.let {
            throw AlTasheratException.Local.RequestValidation(
                clazz = EditPasswordRequest::class,
                errors = it
            )
        }

        repository.editPassword(params)
    }

    private fun validateRequest(request: EditPasswordRequest): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            if (!request.isOldPasswordValid()) put(OLD_PASSWORD, PASSWORD_VALIDATION)
            if (!request.isNewPasswordValid()) put(NEW_PASSWORD, NEW_PASSWORD_VALIDATION)
            if (!request.isConfirmPasswordValid()) put(
                NEW_PASSWORD_CONFIRMATION,
                CONFIRM_PASSWORD_VALIDATION
            )
        }
    }
}