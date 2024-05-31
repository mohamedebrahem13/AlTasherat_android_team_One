package com.solutionplus.altasherat.features.splash.presention.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.domain.models.Country

 interface LanguageContract {

    sealed class LanguageAction : ViewAction {
        data object FetchCountriesFromLocal : LanguageAction()
        data class NextButtonClick(val selectedCountry: String) : LanguageAction()
        data class StartCountriesWorker(val language: String) : LanguageAction()
        data class SpinnerClicked(val selectedCountry: String):LanguageAction()

    }

    sealed class LanguageEvent : ViewEvent {
        // Define events here if needed
        data class UpdateTheCountry(val countries: List<Country>) : LanguageEvent()
        data object NavigateToOnBoarding : LanguageEvent()
        data class StartCountriesWorker(val language: String) : LanguageEvent()
        data class ShowWorkerStateToast(val workerState: String) : LanguageEvent()


    }

     data class LanguageViewState(
         val isLoading: Boolean,
         val selectedCountry: String?,
         val exception: AlTasheratException?,
         val action: ViewAction?
     ) : ViewState {
         companion object {
             fun initial() = LanguageViewState(isLoading = false,selectedCountry=null, exception = null, action = null)
         }
     }
}
