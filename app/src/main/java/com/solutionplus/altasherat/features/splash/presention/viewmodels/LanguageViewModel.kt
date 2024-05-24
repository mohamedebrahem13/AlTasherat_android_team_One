package com.solutionplus.altasherat.features.splash.presention.viewmodels

import androidx.appcompat.app.AppCompatDelegate
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
import com.solutionplus.altasherat.features.splash.presention.contracts.LanguageContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val getCountriesFromLocalUseCase: GetCountriesFromLocalUseCase,
                                            private val countriesWorkerImpl: CountriesWorkerImpl,
                                            private val saveUserPreferenceUseCase: SaveUserPreferenceUseCase
): AlTasheratViewModel<LanguageContract.CountryLocalAction, LanguageContract.CountryLocalEvent, LanguageContract.CountryLocalViewState>(
    LanguageContract.CountryLocalViewState.initial()){
    override fun clearState() {
        setState(LanguageContract.CountryLocalViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageContract.CountryLocalAction.FetchCountriesFromLocal -> fetchCountriesFromLocal()
            // Handle other actions if needed
            is LanguageContract.CountryLocalAction.NextButtonClick ->savePreferenceAndNavigateToOnboarding(action.selectedCountry)
            is LanguageContract.CountryLocalAction.StartCountriesWorkerEn -> startCountriesWorker(action.language)
            is LanguageContract.CountryLocalAction.StartCountriesWorkerAr -> startCountriesWorker(action.language)

            else -> {
                // Do nothing or handle unknown action
            }
        }
    }

    private fun savePreferenceAndNavigateToOnboarding(selectedCountry: String){
       val userPreference= createUserPreference(selectedCountry)
        saveUserPreferenceUseCase.invoke(viewModelScope,userPreference){resource->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    sendEvent(LanguageContract.CountryLocalEvent.NavigateToOnBoarding)
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
                    sendEvent(LanguageContract.CountryLocalEvent.UpdateTheCountry(resource.model))
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
                        sendEvent(LanguageContract.CountryLocalEvent.StartCountriesWorker(language))
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
                sendEvent(LanguageContract.CountryLocalEvent.ShowWorkerStateToast(workInfo.state.toString()+message))

            }
        }
    }

    private fun createUserPreference(preferredCountry: String): UserPreference {
        val language = AppCompatDelegate.getApplicationLocales()
        return UserPreference(preferredCountry, language[0]?.toLanguageTag().toString())
    }

}