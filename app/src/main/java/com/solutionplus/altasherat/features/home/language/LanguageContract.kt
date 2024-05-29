package com.solutionplus.altasherat.features.home.language

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface LanguageContract: ViewAction {
    sealed class LanguageAction : ViewAction {
        data class StartCountriesWorkerEn(val language: String) :LanguageAction()
        data class StartCountriesWorkerAr(val language: String) : LanguageAction()
    }
    sealed class LanguageEvent : ViewEvent {
        data class StartCountriesWorker(val language: String) : LanguageEvent()
        data class ShowWorkerStateToast(val workerState: String) : LanguageEvent()
    }
    data class LanguageViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LanguageViewState(isLoading = false, exception = null, action = null)
        }
    }



}