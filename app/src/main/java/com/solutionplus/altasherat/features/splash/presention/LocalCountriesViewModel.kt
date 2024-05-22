package com.solutionplus.altasherat.features.splash.presention

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.splash.data.worker.CountriesWorkerImpl
import com.solutionplus.altasherat.features.splash.domain.interactor.GetCountriesFromLocalUseCase
import com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import com.solutionplus.altasherat.features.splash.domain.worker.CountriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalCountriesViewModel @Inject constructor(private val getCountriesFromLocalUseCase: GetCountriesFromLocalUseCase,
                                                  private val countriesWorkerImpl: CountriesWorkerImpl,private val saveUserPreferenceUseCase: SaveUserPreferenceUseCase
): AlTasheratViewModel<CountryLocalContract.CountryLocalAction, CountryLocalContract.CountryLocalEvent, CountryLocalContract.CountryLocalViewState>(
    CountryLocalContract.CountryLocalViewState.initial()){
    override fun clearState() {
        setState(CountryLocalContract.CountryLocalViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is CountryLocalContract.CountryLocalAction.FetchCountriesFromLocal -> fetchCountriesFromLocal()
            // Handle other actions if needed
            is CountryLocalContract.CountryLocalAction.NextButtonClick ->navigateToOnboarding(action.userPreference)
            is CountryLocalContract.CountryLocalAction.StartCountriesWorkerEn -> startCountriesWorker(action.language)
            is CountryLocalContract.CountryLocalAction.StartCountriesWorkerAr -> startCountriesWorker(action.language)

            else -> {
                // Do nothing or handle unknown action
            }
        }
    }
    private fun navigateToOnboarding(userPreference: UserPreference){
        saveUserPreferenceUseCase.invoke(viewModelScope,userPreference){resource->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    sendEvent(CountryLocalContract.CountryLocalEvent.NavigateToOnBoarding)
                    // Handle success scenario
                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
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
    private fun startCountriesWorker(language: String) {
        viewModelScope.launch {
            countriesWorkerImpl.startCountriesWorker(language).collect { workInfo ->
                val message = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> {
                        "Worker ENQUEUED"
                    }                    WorkInfo.State.RUNNING -> "Worker RUNNING"
                    WorkInfo.State.SUCCEEDED -> {
                        sendEvent(CountryLocalContract.CountryLocalEvent.StartCountriesWorker(language))
                        val successMessage = workInfo.outputData.getString(CountriesWorker.KEY_SUCCESS_MESSAGE)
                        successMessage ?: "Worker result is null"

                    }
                    WorkInfo.State.FAILED -> {
                        val errorMessage = workInfo.outputData.getString(CountriesWorker.KEY_ERROR_MESSAGE)
                        val failureMessage = "Worker failed with error: $errorMessage"
                        // Log the error message
                        failureMessage
                    }
                    WorkInfo.State.BLOCKED -> "Worker BLOCKED"
                    WorkInfo.State.CANCELLED -> "Worker is cancelled"
                }
                sendEvent(CountryLocalContract.CountryLocalEvent.ShowWorkerStateToast(workInfo.state.toString()+message))

            }
        }
    }


}