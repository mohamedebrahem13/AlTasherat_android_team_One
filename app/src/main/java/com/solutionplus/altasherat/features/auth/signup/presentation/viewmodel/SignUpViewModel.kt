package com.solutionplus.altasherat.features.auth.signup.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.auth.signup.data.model.request.UserSignUpRequest
import com.solutionplus.altasherat.features.auth.signup.domain.interactor.SignUpUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUC: SignUpUC,
    private val getCountryUC: GetCachedCountriesUC,
) : AlTasheratViewModel<SignUpContract.SignUpAction, SignUpContract.SignUpEvent, SignUpContract.SignUpState>(
    SignUpContract.SignUpState.initial()
) {

    init {
        onActionTrigger(SignUpContract.SignUpAction.GetCountries)
    }

    private fun signup(userSignUpRequest: UserSignUpRequest) {
        signUpUC.invoke(viewModelScope, userSignUpRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading, exception = null))
                is Resource.Success -> {
                    sendEvent(SignUpContract.SignUpEvent.SignUpIsSuccessfully(result.model.message))
                }
            }
        }
    }


    private fun getCountries() {
        getCountryUC(viewModelScope) { result ->
            when(result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading, exception = null))
                is Resource.Success -> sendEvent(
                    SignUpContract.SignUpEvent.GetCountries(result.model)
                )
            }
        }
    }


    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action, exception = null))
        when (action) {
            is SignUpContract.SignUpAction.SignUp -> {
                signup(action.userSignUpRequest)
            }
            is SignUpContract.SignUpAction.GetCountries -> {
                getCountries()
            }
        }
    }


    override fun clearState() {
        setState(SignUpContract.SignUpState.initial())
    }
}