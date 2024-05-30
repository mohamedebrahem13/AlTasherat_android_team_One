package com.solutionplus.altasherat.features.home.language.presentation.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface LanguageTwoContract: ViewAction {
    sealed class LanguageTwoContractAction : ViewAction {
        data class StartCountriesWorkerEn(val language: String) : LanguageTwoContractAction()
        data class StartCountriesWorkerAr(val language: String) : LanguageTwoContractAction()
        data object SaveClick : LanguageTwoContractAction()

    }
    sealed class LanguageTwoContractEvent : ViewEvent {
        data class StartCountriesWorker(val language: String) : LanguageTwoContractEvent()
        data class ShowWorkerStateToast(val workerState: String) : LanguageTwoContractEvent()
        data object SaveNavigation : LanguageTwoContractEvent()

    }
    data class LanguageTwoContractViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LanguageTwoContractViewState(isLoading = false, exception = null, action = null)
        }
    }



}