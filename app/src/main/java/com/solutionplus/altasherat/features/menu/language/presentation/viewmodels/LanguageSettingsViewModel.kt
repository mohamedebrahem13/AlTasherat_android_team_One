package com.solutionplus.altasherat.features.menu.language.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
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
                setState(oldViewState.copy(selectedRadio = action.selectedRadio, exception = null))
            }

            is LanguageSettingsContract.LanguageSettingsContractAction.BackClick ->sendEvent(
                LanguageSettingsContract.LanguageSettingsContractEvent.BackNavigation
            )
            is LanguageSettingsContract.LanguageSettingsContractAction.SaveClick ->{
                if (oldViewState.selectedRadio!=null){
                    startCountriesWorker(oldViewState.selectedRadio!!)
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
                        getCountriesAndSaveUserPreference(language)
                        val successMessage = workInfo.outputData.getString(CountriesWorker.KEY_SUCCESS_MESSAGE)
                        successMessage ?: "Worker result is null"


                    }
                    WorkInfo.State.FAILED -> {
                        val errorMessage = workInfo.outputData.getString(CountriesWorker.KEY_ERROR_MESSAGE)
                        val retryException = AlTasheratException.Network.Retrial(
                            messageRes = R.string.check_internet,
                            message = errorMessage
                        )
                        setState(oldViewState.copy(exception = retryException))
                        logger.debug(retryException.message)
                        null // Return null since we don't want a message for the FAILED state

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

    private fun getCountriesAndSaveUserPreference(language: String) {
        getCachedCountriesUC(viewModelScope) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading, exception = null))
                is Resource.Success -> {
                    val countries = result.model
                    fetchUserPreferredCountry(countries)
                    saveUserPreferenceLanguage(language)
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
    private fun updateIsSelectedInCountry(country: Country): Country {
        return country.copy(isSelected = true)
    }

    private fun saveUserPreferenceCountry(country: Country) {
        logger.debug("countrtosave$country")
        val updatedCountry =updateIsSelectedInCountry(country)
        logger.debug("updatedCountry$updatedCountry")

        saveUserPreferenceCountry.invoke(viewModelScope, updatedCountry.toString()) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    logger.debug("success statess${resource.model}")

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
    companion object {
        private val logger = getClassLogger()
    }

}


