package com.solutionplus.altasherat.features.splash.presention

import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

sealed class CountriesContract {

    sealed class Action : ViewAction {
        data object FetchAndSaveCountries : Action()
    }

    sealed class Event : ViewEvent {
        data object CountriesFetchedAndSaved : Event()
    }

    sealed class SplashViewState : ViewState{
        data object Idle : SplashViewState()
        data object Loading : SplashViewState()
        data object Success : SplashViewState()
        data class Error(val message: String) : SplashViewState()
    }


}