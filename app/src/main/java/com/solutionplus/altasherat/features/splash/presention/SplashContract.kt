package com.solutionplus.altasherat.features.splash.presention

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

sealed class SplashContract {

    sealed class SplashAction : ViewAction {
        data object CheckCountryStringKey : SplashAction()
    }

    sealed class SplashEvent : ViewEvent {
        data object NavigateToLanguage : SplashEvent()
        data object NavigateToHome : SplashEvent()
    }

    data class SplashViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SplashViewState(isLoading = false, exception = null, action = null)
        }
    }
}