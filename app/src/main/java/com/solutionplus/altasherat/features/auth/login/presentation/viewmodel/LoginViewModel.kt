package com.solutionplus.altasherat.features.auth.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUC: LoginUC,
    private val getCachedCountriesUC: GetCachedCountriesUC
) : AlTasheratViewModel<LoginContracts.MainAction, LoginContracts.MainEvent, LoginContracts.MainState>(
    LoginContracts.MainState.initial()
) {

    init {
        onActionTrigger(LoginContracts.MainAction.GetCountries)
    }

    private fun login(userLoginRequest: UserLoginRequest) {
        loginUC.invoke(viewModelScope, userLoginRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    sendEvent(LoginContracts.MainEvent.LoginIsSuccessfully(result.model.message!!))
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
                    LoginContracts.MainEvent.GetCountries(result.model)
                )
            }
        }
    }

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is LoginContracts.MainAction.Login -> login(action.loginUserRequest)
            is LoginContracts.MainAction.GetCountries -> getCountries()
        }
    }

    override fun clearState() {
        setState(LoginContracts.MainState.initial())

    }
}