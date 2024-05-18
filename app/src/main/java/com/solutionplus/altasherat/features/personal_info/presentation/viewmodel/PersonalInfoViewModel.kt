package com.solutionplus.altasherat.features.personal_info.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.personal_info.domain.interactor.GetCountriesUC
import com.solutionplus.altasherat.features.personal_info.domain.interactor.GetUserPersonalInfoUC
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoAction
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoEvent
import com.solutionplus.altasherat.features.personal_info.presentation.viewmodel.PersonalInfoContract.PersonalInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
    private val getUserPersonalInfoUC: GetUserPersonalInfoUC
) : AlTasheratViewModel<PersonalInfoAction, PersonalInfoEvent, PersonalInfoState>(PersonalInfoState.initial()) {

    init {
        getCountries()
        getUserPersonalInfo()
    }

    override fun onActionTrigger(action: ViewAction?) {
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
                is Resource.Success -> setState(oldViewState.copy(userPersonalInfo = it.model))
            }
        }
    }

    override fun clearState() {
        setState(PersonalInfoState.initial())
    }
}