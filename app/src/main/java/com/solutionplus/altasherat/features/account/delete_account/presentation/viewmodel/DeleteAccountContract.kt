package com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface DeleteAccountContract {
    sealed class DeleteAccountAction : ViewAction {
    }

    sealed class DeleteAccountEvent : ViewEvent {
    }

    data class DeleteAccountState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?,
    ) : ViewState {
        companion object {
            fun initial() = DeleteAccountState(
                isLoading = false,
                exception = null,
                action = null,
            )
        }
    }
}