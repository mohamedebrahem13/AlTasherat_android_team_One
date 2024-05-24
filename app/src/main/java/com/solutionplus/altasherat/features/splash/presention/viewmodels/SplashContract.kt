package com.solutionplus.altasherat.features.splash.presention.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface SplashContract {

    sealed class SplashAction : ViewAction {
        data object CheckHasCountriesKey : SplashAction()
    }

    sealed class SplashEvent : ViewEvent {
        data object NavigateToLanguage : SplashEvent()
        data object NavigateToHome : SplashEvent()
        data object NavigateToOnBoarding : SplashEvent()

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