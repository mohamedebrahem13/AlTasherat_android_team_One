package com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.account.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.account.personal_info.domain.interactor.UpdatePersonalInfoUC
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.account.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getCountriesUC: GetCachedCountriesUC,
    private val getCachedUserUC: GetCachedUserUC,
    private val getUserUC: GetUserUC,
    private val updatePersonalInfoUC: UpdatePersonalInfoUC,
) : AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        clearState()
        when (action) {
            is PersonalInfoAction.GetCountries -> getCountries()
            is PersonalInfoAction.GetCachedUserPersonalInfo -> getCachedUserPersonalInfo()
            is PersonalInfoAction.UpdatePersonalInfo -> updatePersonalInfo(action.updateInfoRequest)
            is PersonalInfoAction.GetUpdatedUserPersonalInfo -> getUserPersonalInfo()
        }
    }

    private fun getCountries() {
        getCountriesUC.invoke(viewModelScope) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(PersonalInfoEvent.CountriesIndex(it.model))
            }
        }
    }

    private fun getCachedUserPersonalInfo() {
        getCachedUserUC.invoke(viewModelScope) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(PersonalInfoEvent.UserPersonalInfo(it.model))
            }
        }
    }

    private fun getUserPersonalInfo() {
        getUserUC.invoke(viewModelScope) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(PersonalInfoEvent.UserPersonalInfo(it.model))
            }
        }
    }

    private fun updatePersonalInfo(updateInfoRequest: UpdateInfoRequest) {
        updatePersonalInfoUC.invoke(viewModelScope, updateInfoRequest) {
            when (it) {
                is Resource.Failure -> setState(oldViewState.copy(exception = it.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = it.loading))
                is Resource.Success -> sendEvent(PersonalInfoEvent.PersonalInfoUpdated)
            }
        }
    }

    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}