package com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SaveLocalUserTokenUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SaveLocalUserUC
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import com.solutionplus.altasherat.features.auth.signup.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUC: SignUpUC,
    private val saveLocalUserUC: SaveLocalUserUC,
    private val saveTokenUC: SaveLocalUserTokenUC
) : AlTasheratViewModel<SignUpContract.MainAction, SignUpContract.MainEvent, SignUpContract.MainState>(
    SignUpContract.MainState.initial()
) {

    private fun signup(userSignUpRequest: UserSignUpRequest) {
        signUpUC.invoke(viewModelScope, userSignUpRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    sendEvent(SignUpContract.MainEvent.SignUpIsSuccessfully(result.model.message.toString()))
                    saveUser(result.model)
                    saveToken(result.model)
                }
            }
        }
    }

    private fun saveUser(user: UserInfo) {
        viewModelScope.launch {
            saveLocalUserUC.invoke(user).collect { result ->
                when (result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> sendEvent(
                        SignUpContract.MainEvent.UserWasSavedSuccessfully(
                            result.model
                        )
                    )
                }
            }
        }

    }

    private fun saveToken(user: UserInfo) {
        viewModelScope.launch {
            saveTokenUC.invoke(user).collect { result ->
                when (result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> sendEvent(
                        SignUpContract.MainEvent.TokenWasSavedSuccessfully(
                            result.model
                        )
                    )
                }
            }
        }

    }



    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is SignUpContract.MainAction.SignUp -> {
                signup(action.userSignUpRequest)
            }
        }
    }


    override fun clearState() {
        setState(SignUpContract.MainState.initial())
    }
}