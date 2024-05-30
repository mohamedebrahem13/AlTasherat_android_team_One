package com.solutionplus.altasherat.features.personal_info.presentation.viewmodel

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.services.country.domain.models.Country
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface PersonalInfoContract {
    sealed class PersonalInfoAction : ViewAction {
        data object GetCountries : PersonalInfoAction()
        data object GetCachedUserPersonalInfo : PersonalInfoAction()
        data object GetUpdatedUserPersonalInfo : PersonalInfoAction()
        data class UpdatePersonalInfo(val updateInfoRequest: UpdateInfoRequest) :
            PersonalInfoAction()
    }

    sealed class PersonalInfoEvent : ViewEvent {
        data class CountriesIndex(val countries: List<Country>) : PersonalInfoEvent()
        data class UserPersonalInfo(val user: User) : PersonalInfoEvent()
        data object PersonalInfoUpdated : PersonalInfoEvent()
    }

    data class PersonalInfoState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?,
    ) : ViewState {
        companion object {
            fun initial() = PersonalInfoState(
                isLoading = false,
                exception = null,
                action = null,
            )
        }
    }
}