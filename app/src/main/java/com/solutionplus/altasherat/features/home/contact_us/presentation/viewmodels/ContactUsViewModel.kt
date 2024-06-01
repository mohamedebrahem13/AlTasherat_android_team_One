package com.solutionplus.altasherat.features.home.contact_us.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.home.contact_us.domain.repository.intractor.CheckTokenKeyUC
import com.solutionplus.altasherat.features.services.country.domain.interactor.GetCachedCountriesUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getCountryUC: GetCachedCountriesUC,
    private val checkTokenKeyUC: CheckTokenKeyUC,
    private val getCachedUserUC: GetCachedUserUC
) : AlTasheratViewModel<ContactUsContract.ContactUsAction, ContactUsContract.ContactUsEvent, ContactUsContract.ContactUsState>(
    ContactUsContract.ContactUsState.initial()
) {

    init {
        onActionTrigger(ContactUsContract.ContactUsAction.GetCountries)
        checkTokenKeyAndFetchUser()

    }
    private fun checkTokenKeyAndFetchUser() {
        checkTokenKeyUC.invoke(viewModelScope) { tokenResource ->
            when (tokenResource) {
                is Resource.Failure -> {
                    logger.debug("Token check failed: ${tokenResource.exception}")
                    setState(oldViewState.copy(exception = tokenResource.exception))
                }
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = tokenResource.loading))
                }
                is Resource.Success -> {
                    val hasToken = tokenResource.model
                    if (hasToken) {
                        // If the token key exists, fetch the user data
                        fetchUser()
                    } else {
                        // If the token key does not exist, send an event indicating no user key
                        setState(oldViewState.copy(user=null))
                    }
                }
            }
        }
    }
    private fun fetchUser() {
        getCachedUserUC.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    logger.debug("flier${resource}")

                    setState(oldViewState.copy(exception = resource.exception))
                }
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    // User retrieval successful, handle the user data
                 logger.debug("user_success${resource.model}")
                    val user = resource.model
                    setState(oldViewState.copy(user=user))
                }
            }
        }
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
            is ContactUsContract.ContactUsAction.BackClick->{
                sendEvent(ContactUsContract.ContactUsEvent.BackClick)
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
    companion object {
        private val logger = getClassLogger()
    }
}

