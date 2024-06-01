package com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface ContactUsContract {
    sealed class ContactUsAction : ViewAction {
        data object GetCountries : ContactUsAction()
        data object BackClick : ContactUsAction()

    }

    sealed class ContactUsEvent : ViewEvent {
        data class GetCountries(val countries: List<Country>?) : ContactUsEvent()
        data object BackClick : ContactUsEvent()

    }

    data class ContactUsState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ContactUsState(
                isLoading = false, exception = null, action = null
            )
        }
    }

}