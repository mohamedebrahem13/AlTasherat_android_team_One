package com.solutionplus.altasherat.features.splash.presention

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.domain.interactor.GetAndSaveCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAndSaveCountriesUseCase: GetAndSaveCountriesUseCase
) : AlTasheratViewModel<CountriesContract.Action, CountriesContract.Event, CountriesContract.SplashViewState>(
    CountriesContract.SplashViewState.Idle
) {
    override fun clearState() {
        setState(CountriesContract.SplashViewState.Idle)
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is CountriesContract.Action.FetchAndSaveCountries -> fetchAndSaveCountries()
            // Handle other actions if needed
            else -> {
                // Do nothing or handle unknown action
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
                        setState(CountriesContract.SplashViewState.Loading)
                    }
                    is Resource.Success -> {
                        // Update view state to success
                        setState(CountriesContract.SplashViewState.Success)
                        // Send success event
                        sendEvent(CountriesContract.Event.CountriesFetchedAndSaved)
                    }
                    is Resource.Failure -> {
                        // Update view state to error with the error message
                        setState(CountriesContract.SplashViewState.Error(resource.exception.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

}