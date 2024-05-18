package com.solutionplus.altasherat.features.splash.presention

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.HasCountryStringKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAndSaveCountriesUseCase: GetAndSaveCountriesUseCase,
    private val hasCountryStringKeyUseCase: HasCountryStringKeyUseCase
) : AlTasheratViewModel<SplashContract.Action, SplashContract.Event, SplashContract.SplashViewState>(
    SplashContract.SplashViewState.Idle
) {
    override fun clearState() {
        setState(SplashContract.SplashViewState.Idle)
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is SplashContract.Action.CheckCountryStringKey -> checkCountryStringKey()
            is SplashContract.Action.FetchAndSaveCountries -> fetchAndSaveCountries()
            // Handle other actions if needed
            else -> {
                // Do nothing or handle unknown action
            }
        }
    }
    private fun checkCountryStringKey() {
        viewModelScope.launch {
            hasCountryStringKeyUseCase.executeAndEmitState().collect { resource ->
                when (resource) {
                    is Resource.Failure -> {
                        setState(
                            SplashContract.SplashViewState.Error(
                                resource.exception.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Progress -> {
                        // Update view state to loading
                        setState(SplashContract.SplashViewState.Loading)
                    }
                    is Resource.Success -> {
                        if (resource.model) {
                            sendEvent(SplashContract.Event.NavigateToHome)
                        } else {
                            fetchAndSaveCountries()
                        }
                    }
                }
            }
        }
    }

    private fun fetchAndSaveCountries() {
        // Call the invoke function of the use case and collect the states emitted by it
        viewModelScope.launch {
            getAndSaveCountriesUseCase.executeAndEmitState().collect { resource ->
                when (resource) {
                     is Resource.Progress -> {
                        // Update view state to loading
                        setState(SplashContract.SplashViewState.Loading)
                    }
                    is Resource.Success -> {
                        // Update view state to success
                        setState(SplashContract.SplashViewState.Success)
                        // Send success event
                        sendEvent(SplashContract.Event.NavigateToOnBoarding)
                    }
                    is Resource.Failure -> {
                        // Update view state to error with the error message
                        setState(SplashContract.SplashViewState.Error(resource.exception.message ?: "Unknown error"))
                    }
                }
            }
        }
    }


}