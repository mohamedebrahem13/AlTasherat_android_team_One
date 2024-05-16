package com.solutionplus.altasherat.features.auth.signup.presentation.contracts

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest

interface LoginContracts {
    sealed class MainAction : ViewAction {
        data object GetCountries : MainAction()
        data class Login(val loginUserRequest: LoginRequest) : MainAction()
    }

    sealed class MainEvent : ViewEvent {
        data class LoginIsSuccessfully(val message: String) : MainEvent()
    }

    data class MainState(
        val isLoading: Boolean, val exception: AlTasheratException?, val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = MainState(
                isLoading = false, exception = null, action = null
            )
        }
    }
}










