package com.solutionplus.altasherat.features.splash.presention.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState

interface OnBoardingThreeContract {

    sealed class OnBoardingThreeAction : ViewAction {
        data object SaveOnboardingShown : OnBoardingThreeAction()
    }

    sealed class OnBoardingThreeEvent : ViewEvent {
        data object NavigateToHome : OnBoardingThreeEvent()
    }

    data class OnBoardingThreeViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = OnBoardingThreeViewState(isLoading = false, exception = null, action = null)
        }
    }
}