package com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest

interface EditPasswordContract {
    sealed class EditPasswordAction : ViewAction {
        data class EditPassword(val request: EditPasswordRequest) : EditPasswordAction()
    }

    sealed class EditPasswordEvent : ViewEvent {
        data class PasswordUpdated(val message: String) : EditPasswordEvent()
    }

    data class EditPasswordState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?,
    ) : ViewState {
        companion object {
            fun initial() = EditPasswordState(
                isLoading = false,
                exception = null,
                action = null,
            )
        }
    }
}