package com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface SignUpContract {
    sealed class SignUpAction : ViewAction {
        data object GetCountries : SignUpAction()
        data class SignUp(val userSignUpRequest: UserSignUpRequest) : SignUpAction()
    }

    sealed class SignUpEvent : ViewEvent {
        data class SignUpIsSuccessfully(val message: String) : SignUpEvent()
        data class GetCountries(val countries: List<Country>?) : SignUpEvent()
    }

    data class SignUpState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = SignUpState(
                isLoading = false, exception = null, action = null
            )
        }
    }
}










