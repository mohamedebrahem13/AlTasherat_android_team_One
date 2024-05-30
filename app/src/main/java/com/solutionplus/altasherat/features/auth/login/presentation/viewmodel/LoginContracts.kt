package com.solutionplus.altasherat.features.auth.login.presentation.viewmodel

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.services.country.domain.models.Country

interface LoginContracts {
    sealed class LoginAction : ViewAction {
        data object GetCountries : LoginAction()
        data class Login(val loginUserRequest: UserLoginRequest) : LoginAction()
    }

    sealed class LoginEvent : ViewEvent {
        data class LoginIsSuccessfully(val message: String) : LoginEvent()
        data class GetCountries(val countries: List<Country>?) : LoginEvent()
    }

    data class LoginState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = LoginState(
                isLoading = false, exception = null, action = null
            )
        }
    }
}










