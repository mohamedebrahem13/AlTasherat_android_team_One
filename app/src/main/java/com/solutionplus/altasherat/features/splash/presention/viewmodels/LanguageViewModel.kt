package com.solutionplus.altasherat.features.splash.presention.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.country.data.worker.CountriesWorkerImpl
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.splash.domain.interactor.GetUserPreferredCountryUC
import com.solutionplus.altasherat.features.splash.domain.interactor.SaveUserPreferenceUseCase
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference
import com.solutionplus.altasherat.features.services.country.domain.worker.CountriesWorker
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val getCountriesFromLocalUseCase: GetCachedCountriesUC,
                                            private val countriesWorkerImpl: CountriesWorkerImpl,
                                            private val saveUserPreferenceUseCase: SaveUserPreferenceUseCase,
                                            private val getUserPreferredCountryUC: GetUserPreferredCountryUC

): AlTasheratViewModel<LanguageContract.LanguageAction, LanguageContract.LanguageEvent, LanguageContract.LanguageViewState>(
    LanguageContract.LanguageViewState.initial()){
    override fun clearState() {
        setState(LanguageContract.LanguageViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageContract.LanguageAction.SpinnerClicked->  {
                setState(oldViewState.copy(selectedCountry=action.selectedCountry))
            }
            is LanguageContract.LanguageAction.FetchCountriesFromLocal -> fetchCountriesFromLocal()
            // Handle other actions if needed
            is LanguageContract.LanguageAction.NextButtonClick ->savePreferenceAndNavigateToOnboarding(action.selectedCountry)
            is LanguageContract.LanguageAction.StartCountriesWorkerEn -> startCountriesWorker(action.language)
            is LanguageContract.LanguageAction.StartCountriesWorkerAr -> startCountriesWorker(action.language)

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
                    sendEvent(LanguageContract.LanguageEvent.NavigateToOnBoarding)
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
                    // Fetch the user's preferred country
                    fetchUserPreferredCountry(resource.model)
                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
            }
        }
    }
    private fun fetchUserPreferredCountry(countries: List<Country>) {
        getUserPreferredCountryUC.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    // If the user preferred country is not null, update the list of countries accordingly
                    resource.model.let {
                        // Update the list of countries based on the preferred language
                        sendEvent(LanguageContract.LanguageEvent.UpdateTheCountry(countries))
                        setState(oldViewState.copy(selectedCountry = resource.model))

                    }
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
                        sendEvent(LanguageContract.LanguageEvent.StartCountriesWorker(language))
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
                sendEvent(LanguageContract.LanguageEvent.ShowWorkerStateToast(workInfo.state.toString()+message))

            }
        }
    }

    private fun createUserPreference(preferredCountry: String): UserPreference {
        val language = AppCompatDelegate.getApplicationLocales()
        val preferredLanguage = if (language.isEmpty) {
            "ar" // Default to "ar" if device locale is empty
        } else {
            language[0]?.toLanguageTag().toString()
        }
        return UserPreference(preferredCountry, preferredLanguage)
    }

}