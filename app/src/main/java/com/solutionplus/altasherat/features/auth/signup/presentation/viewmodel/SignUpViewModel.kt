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
) : AlTasheratViewModel<SignUpContract.MainAction, SignUpContract.MainEvent, SignUpContract.MainState>(
    SignUpContract.MainState.initial()
) {

    init {
        onActionTrigger(SignUpContract.MainAction.GetCountries)
    }

    private fun signup(userSignUpRequest: UserSignUpRequest) {
        signUpUC.invoke(viewModelScope, userSignUpRequest) { result ->
            when (result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    sendEvent(SignUpContract.MainEvent.SignUpIsSuccessfully(result.model.message.toString()))
                }
            }
        }
    }


    private fun getCountries() {
        getCountryUC(viewModelScope) { result ->
            when(result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> sendEvent(
                    SignUpContract.MainEvent.GetCountries(result.model)
                )
            }
        }
    }


    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is SignUpContract.MainAction.SignUp -> {
                signup(action.userSignUpRequest)
            }
            is SignUpContract.MainAction.GetCountries -> {
                getCountries()
            }
        }
    }


    override fun clearState() {
        setState(SignUpContract.MainState.initial())
    }
}