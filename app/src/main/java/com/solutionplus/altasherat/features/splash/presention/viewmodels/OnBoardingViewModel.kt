package com.solutionplus.altasherat.features.splash.presention.viewmodels

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.domain.interactor.SetOnboardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel@Inject constructor(private val setOnboardingShownUseCase: SetOnboardingShownUseCase): AlTasheratViewModel<OnBoardingContract.OnBoardingAction, OnBoardingContract.OnBoardingEvent, OnBoardingContract.OnBoardingViewState>(
    OnBoardingContract.OnBoardingViewState.initial()
) {
    override fun clearState() {
        setState(OnBoardingContract.OnBoardingViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is OnBoardingContract.OnBoardingAction.SaveOnboardingShown -> {
                // Execute the use case to save the onboarding shown boolean to true
                executeSaveOnboardingShownUseCase()
            }
            // Handle other actions if needed
        }
    }
    private fun executeSaveOnboardingShownUseCase() {
            setOnboardingShownUseCase.invoke(viewModelScope,true){ resource ->
                when (resource) {
                    is Resource.Failure -> {

                        setState(oldViewState.copy(exception = resource.exception))
                    }
                    is Resource.Progress -> {
                        setState(oldViewState.copy(isLoading = resource.loading))

                    }
                    is Resource.Success -> {
                        sendEvent(OnBoardingContract.OnBoardingEvent.NavigateToHome)

                    }
                }

            }

    }
}