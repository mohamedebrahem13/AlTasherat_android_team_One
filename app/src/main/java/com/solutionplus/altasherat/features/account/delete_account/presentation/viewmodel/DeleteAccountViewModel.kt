package com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.account.delete_account.domain.interactor.DeleteAccountUC
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountAction
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountEvent
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountUC: DeleteAccountUC
) : AlTasheratViewModel<DeleteAccountAction, DeleteAccountEvent, DeleteAccountState>(
    DeleteAccountState.initial()
) {
    override fun onActionTrigger(action: ViewAction?) {
        clearState()
        when (action) {
            is DeleteAccountAction.DeleteAccount -> deleteAccount(action.password)
        }
    }

    private fun deleteAccount(password: String) {
        deleteAccountUC.invoke(viewModelScope, password) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(DeleteAccountEvent.AccountDeleted)
            }
        }
    }

    override fun clearState() {
        setState(DeleteAccountState.initial())
    }
}