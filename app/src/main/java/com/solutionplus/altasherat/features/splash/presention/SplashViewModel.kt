package com.solutionplus.altasherat.features.splash.presention

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.HasCountryStringKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAndSaveCountriesUseCase: GetAndSaveCountriesUseCase,
    private val hasCountryStringKeyUseCase: HasCountryStringKeyUseCase
) : AlTasheratViewModel<SplashContract.SplashAction, SplashContract.SplashEvent, SplashContract.SplashViewState>(
    SplashContract.SplashViewState.initial()

) {
    override fun clearState() {
        setState(SplashContract.SplashViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is SplashContract.SplashAction.CheckCountryStringKey -> checkCountryStringKey()
            // Handle other actions if needed
            else -> {
                // Do nothing or handle unknown action
            }
        }
    }
    private fun checkCountryStringKey() {
        hasCountryStringKeyUseCase.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Failure -> {

                    setState(oldViewState.copy(exception = resource.exception))
                }
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))

                }
                is Resource.Success -> {
                    if (resource.model) {
                        sendEvent(SplashContract.SplashEvent.NavigateToHome)
                    } else {
                        fetchAndSaveCountries()
                    }
                }
            }
        }
    }

    private fun fetchAndSaveCountries() {
        getAndSaveCountriesUseCase.invoke(viewModelScope,params = "ar") { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    sendEvent(SplashContract.SplashEvent.NavigateToOnBoarding)
                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
            }
        }
    }
}

