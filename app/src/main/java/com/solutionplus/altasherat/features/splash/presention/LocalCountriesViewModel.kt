package com.solutionplus.altasherat.features.splash.presention

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.domain.interactor.GetCountriesFromLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalCountriesViewModel @Inject constructor(private val getCountriesFromLocalUseCase: GetCountriesFromLocalUseCase): AlTasheratViewModel<CountryLocalContract.CountryLocalAction, CountryLocalContract.CountryLocalEvent, CountryLocalContract.CountryLocalViewState>(
    CountryLocalContract.CountryLocalViewState.initial()){
    override fun clearState() {
        setState(CountryLocalContract.CountryLocalViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is CountryLocalContract.CountryLocalAction.FetchCountriesFromLocal -> fetchCountriesFromLocal()
            // Handle other actions if needed
            else -> {
                // Do nothing or handle unknown action
            }
        }
    }

    private fun fetchCountriesFromLocal() {
        getCountriesFromLocalUseCase.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    sendEvent(CountryLocalContract.CountryLocalEvent.UpdateTheCountry(resource.model))
                    // Handle success scenario
                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
            }
        }
    }


}