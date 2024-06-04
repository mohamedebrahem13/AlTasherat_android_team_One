package com.solutionplus.altasherat.features.menu.language.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.google.gson.Gson
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.menu.language.domain.repository.interactor.GetUserPreferredCountryUC
import com.solutionplus.altasherat.features.menu.language.domain.repository.interactor.SaveUserPreferenceLanguageUseCase
import com.solutionplus.altasherat.features.menu.language.domain.repository.interactor.SaveUserPreferredCountryUseCase
import com.solutionplus.altasherat.features.services.country.data.worker.CountriesWorkerImpl
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.country.domain.worker.CountriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LanguageSettingsViewModel@Inject constructor(private val countriesWorkerImpl: CountriesWorkerImpl,
                                                   private val saveUserPreferenceLanguageUseCase: SaveUserPreferenceLanguageUseCase,
                                                   private val getCachedCountriesUC:GetCachedCountriesUC,
                                                   private val getUserPreferredCountryUC: GetUserPreferredCountryUC,
                                                   private val saveUserPreferenceCountry: SaveUserPreferredCountryUseCase) : AlTasheratViewModel <LanguageSettingsContract.LanguageSettingsContractAction, LanguageSettingsContract.LanguageSettingsContractEvent, LanguageSettingsContract.LanguageSettingsContractViewState>(
    LanguageSettingsContract.LanguageSettingsContractViewState.initial()
) {
    override fun clearState() {
        setState(LanguageSettingsContract.LanguageSettingsContractViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageSettingsContract.LanguageSettingsContractAction.RadioButtonClick ->{
                setState(oldViewState.copy(selectedRadio = action.selectedRadio))
            }

            is LanguageSettingsContract.LanguageSettingsContractAction.BackClick ->sendEvent(
                LanguageSettingsContract.LanguageSettingsContractEvent.BackNavigation
            )
            is LanguageSettingsContract.LanguageSettingsContractAction.SaveClick ->startCountriesWorker(oldViewState.selectedRadio.toString())
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
                        saveUserPreferenceLanguage(language)
                        getCountries()
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
                sendEvent(
                    LanguageSettingsContract.LanguageSettingsContractEvent.ShowWorkerStateToast(
                        workInfo.state.toString() + message
                    )
                )

            }
        }
    }

    private fun getCountries() {
        getCachedCountriesUC(viewModelScope) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading, exception = null))
                is Resource.Success -> {
                    val countries = result.model
                    fetchUserPreferredCountry(countries)
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
                    val preferredCountryString = resource.model
                    val countryId = extractCountryId(preferredCountryString)
                    val matchedCountry = countries.find { it.id == countryId }

                    if (matchedCountry != null) {
                        saveUserPreferenceCountry(matchedCountry)
                    }
                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
            }
        }
    }
    private fun extractCountryId(countryString: String): Int? {
        return countryString.substringAfter("id=").substringBefore(",").toIntOrNull()
    }

    private fun saveUserPreferenceCountry(country: Country) {
        saveUserPreferenceCountry.invoke(viewModelScope, country.toString()) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {

                }
                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))
                }
            }
        }
    }


    private fun saveUserPreferenceLanguage(language: String) {

        saveUserPreferenceLanguageUseCase.invoke(viewModelScope, language) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }

                is Resource.Success -> {
                    sendEvent(
                        LanguageSettingsContract.LanguageSettingsContractEvent.SaveNavigation(
                            language
                        )
                    )
                }

                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))

                }
            }

        }
    }


}
