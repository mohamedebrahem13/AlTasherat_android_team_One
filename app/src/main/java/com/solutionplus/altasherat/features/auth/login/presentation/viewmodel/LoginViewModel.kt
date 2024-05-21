package com.solutionplus.altasherat.features.auth.login.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.domain.interactor.LoginUC
import com.solutionplus.altasherat.features.auth.login.domain.interactor.SaveLoginTokenUC
import com.solutionplus.altasherat.features.auth.login.domain.interactor.SaveLoginUserUC
import com.solutionplus.altasherat.features.auth.login.domain.models.LoginUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUC: LoginUC,
    private val saveLoginUserUC: SaveLoginUserUC,
    private val saveLoginTokenUC: SaveLoginTokenUC
) : AlTasheratViewModel<LoginContracts.MainAction, LoginContracts.MainEvent, LoginContracts.MainState>(
    LoginContracts.MainState.initial()
) {


    private fun login(userLoginRequest: UserLoginRequest) {
        loginUC.invoke(viewModelScope, userLoginRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    sendEvent(LoginContracts.MainEvent.LoginIsSuccessfully(result.model.message!!))
                    saveUser(result.model)
                    saveToken(result.model)
                }
            }
        }
    }

    private fun saveUser(user: LoginUserInfo) {
        viewModelScope.launch {
            saveLoginUserUC.invoke(user).collect { result ->
                when (result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> sendEvent(
                        LoginContracts.MainEvent.UserWasSavedSuccessfully(
                            result.model
                        )
                    )
                }
            }
        }

    }

    private fun saveToken(user: LoginUserInfo) {
        viewModelScope.launch {
            saveLoginTokenUC.invoke(user).collect { result ->
                when (result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> sendEvent(
                        LoginContracts.MainEvent.TokenWasSavedSuccessfully(
                            result.model
                        )
                    )
                }
            }
        }

    }


    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is LoginContracts.MainAction.Login -> login(action.loginUserRequest)
        }
    }

    override fun clearState() {
        setState(LoginContracts.MainState.initial())

    }
}