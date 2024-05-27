package com.solutionplus.altasherat.features.home.profile.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getCachedUserUC:GetCachedUserUC) :
    AlTasheratViewModel<ProfileContract.ProfileAction, ProfileContract.ProfileEvent, ProfileContract.ProfileViewState>(ProfileContract.ProfileViewState.initial()) {
        init {
            fetchUser()
        }

    private fun fetchUser() {
        getCachedUserUC.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    logger.debug("falier${resource}")

                    setState(oldViewState.copy(exception = resource.exception))
                }
                is Resource.Progress -> {
                    setState(oldViewState.copy(isLoading = resource.loading))
                }
                is Resource.Success -> {
                    // User retrieval successful, handle the user data
                    logger.debug("user_success${resource.model}")
                    val user = resource.model
                    sendEvent(ProfileContract.ProfileEvent.UserLoaded(user))
                }
            }
        }
    }

    override fun clearState() {
        setState(ProfileContract.ProfileViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        // Handle incoming actions specific to ProfileViewModel
    }
    companion object {
        private val logger = getClassLogger()
    }
}