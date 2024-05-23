package com.solutionplus.altasherat.features.splash.presention.contracts

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.splash.domain.models.Country
import com.solutionplus.altasherat.features.splash.domain.models.UserPreference

sealed class CountryLocalContract {

    sealed class CountryLocalAction : ViewAction {
        data object FetchCountriesFromLocal: CountryLocalAction()
        data class NextButtonClick(val userPreference: UserPreference) : CountryLocalAction()
        data class StartCountriesWorkerEn(val language: String) : CountryLocalAction()
        data class StartCountriesWorkerAr(val language: String) : CountryLocalAction()

    }

    sealed class CountryLocalEvent : ViewEvent {
        // Define events here if needed
        data class UpdateTheCountry(val countries: List<Country>) : CountryLocalEvent()
        data object NavigateToOnBoarding : CountryLocalEvent()
        data class StartCountriesWorker(val language: String) : CountryLocalEvent()
        data class ShowWorkerStateToast(val workerState: String) : CountryLocalEvent()


    }

    data class CountryLocalViewState(
        val isLoading: Boolean,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = CountryLocalViewState(isLoading = false, exception = null, action = null)
        }
    }
}
