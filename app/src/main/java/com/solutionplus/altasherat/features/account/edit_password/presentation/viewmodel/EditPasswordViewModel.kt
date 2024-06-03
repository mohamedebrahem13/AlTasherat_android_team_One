package com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.account.edit_password.data.models.request.EditPasswordRequest
import com.solutionplus.altasherat.features.account.edit_password.domain.interactor.EditPasswordUC
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordAction
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordEvent
import com.solutionplus.altasherat.features.account.edit_password.presentation.viewmodel.EditPasswordContract.EditPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val editPasswordUC: EditPasswordUC
) : AlTasheratViewModel<EditPasswordAction, EditPasswordEvent, EditPasswordState>(EditPasswordState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is EditPasswordAction.EditPassword -> editPassword(action.request)
        }
    }

    private fun editPassword(request: EditPasswordRequest) {
        editPasswordUC.invoke(viewModelScope, request) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(EditPasswordEvent.PasswordUpdated)
            }
        }
    }

    override fun clearState() {
        setState(EditPasswordState.initial())
    }
}