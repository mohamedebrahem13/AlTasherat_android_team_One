package com.solutionplus.altasherat.features.personal_info.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.personal_info.data.models.request.UpdateInfoRequest
import com.solutionplus.altasherat.features.personal_info.domain.interactor.UpdatePersonalInfoUC
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoState
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getCountriesUC: GetCachedCountriesUC,
    private val getUserPersonalInfoUC: GetCachedUserUC,
    private val updatePersonalInfoUC: UpdatePersonalInfoUC,
) : AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    override fun onActionTrigger(action: ViewAction?) {
        when (action) {
            is PersonalInfoAction.GetCountries -> getCountries()
            is PersonalInfoAction.GetUserPersonalInfo -> getUserPersonalInfo()
            is PersonalInfoAction.UpdatePersonalInfo -> updatePersonalInfo(action.updateInfoRequest)
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

    private fun getUserPersonalInfo() {
        getUserPersonalInfoUC.invoke(viewModelScope) {
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