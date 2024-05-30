package com.solutionplus.altasherat.features.splash.presention.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface OnBoardingContract {

    sealed class OnBoardingAction : ViewAction {
        data object SaveOnboardingShown : OnBoardingAction()
    }

    sealed class OnBoardingEvent : ViewEvent {
        data object NavigateToHome : OnBoardingEvent()
    }

    data class OnBoardingViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = OnBoardingViewState(isLoading = false, exception = null, action = null)
        }
    }
}