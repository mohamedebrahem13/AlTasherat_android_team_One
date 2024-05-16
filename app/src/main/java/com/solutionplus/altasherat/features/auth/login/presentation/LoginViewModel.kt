package com.solutionplus.altasherat.features.auth.login.presentation

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.login.data.models.request.LoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginUC
import com.solutionplus.altasherat.features.auth.signup.presentation.contracts.LoginContracts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUC: LoginUC
): AlTasheratViewModel<LoginContracts.MainAction, LoginContracts.MainEvent, LoginContracts.MainState>(
    LoginContracts.MainState.initial()) {


    private fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUC.invoke(loginRequest).collect { result ->
                when(result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> sendEvent(LoginContracts.MainEvent.LoginIsSuccessfully(result.model.token))
                }
            }
        }

    }

    override fun onActionTrigger(action: ViewAction?) {
        when(action) {
            is LoginContracts.MainAction.Login -> login(action.loginUserRequest)
        }
    }

    override fun clearState() {
        setState(LoginContracts.MainState.initial())

    }
}