package com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel

import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountAction
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountEvent
import com.solutionplus.altasherat.features.account.delete_account.presentation.viewmodel.DeleteAccountContract.DeleteAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
) : AlTasheratViewModel<DeleteAccountAction, DeleteAccountEvent, DeleteAccountState>(
    DeleteAccountState.initial()
) {

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
        }
    }


    override fun clearState() {
        setState(DeleteAccountState.initial())
    }
}