package com.solutionplus.altasherat.features.splash.presention

import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

sealed class SplashContract {
    sealed class SplashAction : ViewAction {
        data object CheckCountryStringKey : SplashAction()
    }
    sealed class SplashEvent : ViewEvent {
        data object NavigateToOnBoarding : SplashEvent()
        data object NavigateToHome : SplashEvent()
    }
    sealed class SplashViewState : ViewState{
        data object Idle : SplashViewState()
        data object Loading : SplashViewState()
        data object Success : SplashViewState()
        data class Error(val message: String) : SplashViewState()
    }
}