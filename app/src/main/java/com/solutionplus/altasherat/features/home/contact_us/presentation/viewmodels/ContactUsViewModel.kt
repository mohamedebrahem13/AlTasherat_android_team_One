package com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getCountryUC: GetCachedCountriesUC,
) : AlTasheratViewModel<ContactUsContract.ContactUsAction, ContactUsContract.ContactUsEvent, ContactUsContract.ContactUsState>(
    ContactUsContract.ContactUsState.initial()
) {

    init {
        onActionTrigger(ContactUsContract.ContactUsAction.GetCountries)
    }
    override fun clearState() {
        setState(ContactUsContract.ContactUsState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action, exception = null))
        when (action) {
            is ContactUsContract.ContactUsAction.GetCountries-> {
                getCountries()
            }
        }
    }

    private fun getCountries() {
        getCountryUC(viewModelScope) { result ->
            when(result) {
                is Resource.Failure -> setState(oldViewState.copy(exception = result.exception))
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading, exception = null))
                is Resource.Success -> sendEvent(
                    ContactUsContract.ContactUsEvent.GetCountries(result.model)
                )
            }
        }
    }

}