package com.solutionplus.altasherat.features.account.edit_password.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD
import com.solutionplus.altasherat.common.domain.constants.Constants.NEW_PASSWORD_CONFIRMATION
import com.solutionplus.altasherat.common.domain.constants.Constants.OLD_PASSWORD
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.domain.repository.IEditPasswordRepository

class EditPasswordUC(
    private val repository: IEditPasswordRepository
) : BaseUseCase<String, EditPasswordRequest>() {

    override suspend fun execute(params: EditPasswordRequest?): String {
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

        return repository.editPassword(params)
    }

    private fun validateRequest(request: EditPasswordRequest): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            if (!request.isOldPasswordValid()) put(OLD_PASSWORD, R.string.password_validation)
            if (!request.isNewPasswordValid()) put(NEW_PASSWORD, R.string.new_password_validation)
            if (!request.isConfirmPasswordValid()) put(
                NEW_PASSWORD_CONFIRMATION,
                R.string.confirm_password_validation
            )
        }
    }
}