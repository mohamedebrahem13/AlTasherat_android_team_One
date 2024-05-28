package com.solutionplus.altasherat.features.auth.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginWithPhoneUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithPhoneUC: LoginWithPhoneUC,
    private val getCachedCountriesUC: GetCachedCountriesUC
) : AlTasheratViewModel<LoginContracts.LoginAction, LoginContracts.LoginEvent, LoginContracts.LoginState>(
    LoginContracts.LoginState.initial()
) {

    init {
        onActionTrigger(LoginContracts.LoginAction.GetCountries)
    }

    private fun login(userLoginRequest: UserLoginRequest) {
        loginWithPhoneUC.invoke(viewModelScope, userLoginRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    sendEvent(LoginContracts.LoginEvent.LoginIsSuccessfully(result.model.message!!))
                }
            }
        }
    }

    private fun getCountries() {
        getCachedCountriesUC(viewModelScope) { result ->
            when(result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> sendEvent(
                    LoginContracts.LoginEvent.GetCountries(result.model)
                )
            }
        }
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is LoginContracts.LoginAction.Login -> login(action.loginUserRequest)
            is LoginContracts.LoginAction.GetCountries -> getCountries()
        }
    }

    override fun clearState() {
        setState(LoginContracts.LoginState.initial())

    }
}