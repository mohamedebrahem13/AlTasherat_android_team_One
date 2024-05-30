package com.solutionplus.altasherat.features.home.language.presentation.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface LanguageSettingsContract: ViewAction {
    sealed class LanguageSettingsContractAction : ViewAction {
        data class StartCountriesWorker(val language: String) : LanguageSettingsContractAction()
        data object SaveClick : LanguageSettingsContractAction()
        data object BackClick : LanguageSettingsContractAction()


    }
    sealed class LanguageSettingsContractEvent : ViewEvent {
        data class StartCountriesWorker(val language: String) : LanguageSettingsContractEvent()
        data class ShowWorkerStateToast(val workerState: String) : LanguageSettingsContractEvent()
        data object SaveNavigation : LanguageSettingsContractEvent()
        data object BackNavigation : LanguageSettingsContractEvent()


    }
    data class LanguageSettingsContractViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LanguageSettingsContractViewState(isLoading = false, exception = null, action = null)
        }
    }



}