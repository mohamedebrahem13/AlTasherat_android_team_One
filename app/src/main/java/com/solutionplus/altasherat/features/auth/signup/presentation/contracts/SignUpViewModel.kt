package com.solutionplus.altasherat.features.auth.signup.presentation.contracts

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserRequest
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUC: SignUpUC
): AlTasheratViewModel<SignUpContract.MainAction, SignUpContract.MainEvent, SignUpContract.MainState>(
    SignUpContract.MainState.initial()
) {


    private fun signup(userRequest: UserRequest) {
        viewModelScope.launch {
            signUpUC.invoke(userRequest).collect { result ->
                when(result) {
                    is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> {
                        Log.d("currentState", "Successful")
                        sendEvent(SignUpContract.MainEvent.SignUpIsSuccessfully("SignUpSuccessfully"))
                    }
                }
            }
        }
    }


    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is SignUpContract.MainAction.SignUp -> { signup(action.userRequest)}
        }
    }


    override fun clearState() {
        setState(SignUpContract.MainState.initial())
    }
}