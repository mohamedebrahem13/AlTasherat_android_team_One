package com.solutionplus.altasherat.features.splash.presention

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.splash.domain.models.Country

sealed class CountryLocalContract {

    sealed class CountryLocalAction : ViewAction {
        data object FetchCountriesFromLocal: CountryLocalAction()
    }

    sealed class CountryLocalEvent : ViewEvent {
        // Define events here if needed
        data class UpdateTheCountry(val countries: List<Country>) : CountryLocalEvent()

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
