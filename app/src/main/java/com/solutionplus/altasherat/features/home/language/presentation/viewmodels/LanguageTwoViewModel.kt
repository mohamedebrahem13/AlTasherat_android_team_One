package com.solutionplus.altasherat.features.home.language.presentation.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.home.language.domain.repository.intractor.SaveUserPreferenceLanguageUseCase
import com.solutionplus.altasherat.features.services.country.data.worker.CountriesWorkerImpl
import com.solutionplus.altasherat.features.services.country.domain.worker.CountriesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LanguageTwoViewModel@Inject constructor(private val countriesWorkerImpl: CountriesWorkerImpl,private val saveUserPreferenceLanguageUseCase: SaveUserPreferenceLanguageUseCase) : AlTasheratViewModel <LanguageTwoContract.LanguageTwoContractAction, LanguageTwoContract.LanguageTwoContractEvent, LanguageTwoContract.LanguageTwoContractViewState>(
    LanguageTwoContract.LanguageTwoContractViewState.initial()) {
    override fun clearState() {
        setState(LanguageTwoContract.LanguageTwoContractViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is LanguageTwoContract.LanguageTwoContractAction.BackClick->sendEvent(LanguageTwoContract.LanguageTwoContractEvent.BackNavigation)
            is LanguageTwoContract.LanguageTwoContractAction.SaveClick->saveUserPreferenceLanguage()
            is LanguageTwoContract.LanguageTwoContractAction.StartCountriesWorkerEn -> startCountriesWorker(action.language)
            is LanguageTwoContract.LanguageTwoContractAction.StartCountriesWorkerAr -> startCountriesWorker(action.language)
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
                        sendEvent(LanguageTwoContract.LanguageTwoContractEvent.StartCountriesWorker(language))
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
                sendEvent(LanguageTwoContract.LanguageTwoContractEvent.ShowWorkerStateToast(workInfo.state.toString()+message))

            }
        }
    }

    private fun saveUserPreferenceLanguage() {
        val preferredLanguage = createUserPreferenceLanguage()

        saveUserPreferenceLanguageUseCase.invoke(viewModelScope, preferredLanguage) { resource ->
            when (resource) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }

                is Resource.Success -> {
                    sendEvent(LanguageTwoContract.LanguageTwoContractEvent.SaveNavigation)
                }

                is Resource.Failure -> {
                    setState(oldViewState.copy(exception = resource.exception))

                }
            }

        }
    }

    private fun createUserPreferenceLanguage(): String {
        val language = AppCompatDelegate.getApplicationLocales()
        val preferredLanguage = if (language.isEmpty) {
            "ar" // Default to "ar" if device locale is empty
        } else {
            language[0]?.toLanguageTag().toString()
        }
        return  preferredLanguage
    }
}
