package com.solutionplus.altasherat.features.home.profile.presentation.viewmodels

import android.os.Message
import androidx.lifecycle.viewModelScope
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.presentation.viewmodel.AlTasheratViewModel
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.features.home.profile.domain.intractor.DeleteUserInfoAndTokenUC
import com.solutionplus.altasherat.features.home.profile.domain.intractor.LogoutUC
import com.solutionplus.altasherat.features.services.user.domain.interactor.GetCachedUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getCachedUserUC:GetCachedUserUC, private val logoutUC: LogoutUC,private val deleteUserInfoAndTokenUC:DeleteUserInfoAndTokenUC) :
    AlTasheratViewModel<ProfileContract.ProfileAction, ProfileContract.ProfileEvent, ProfileContract.ProfileViewState>(ProfileContract.ProfileViewState.initial()) {
        init {
            fetchUser()
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
   private fun logout(){
       logoutUC.invoke(viewModelScope){resource ->
           when (resource) {
               is Resource.Failure -> {
                   logger.debug("sign out,${resource.exception.message}")
                   setState(oldViewState.copy(exception = resource.exception))
               }
               is Resource.Progress -> {
                   setState(oldViewState.copy(isLoading = resource.loading))
               }
               is Resource.Success -> {
                   val message = resource.model
                   logger.debug("sign out,$message")
                   deleteUserInfoAndToken(message)
               }
           }
       }
   }
    private fun deleteUserInfoAndToken(message: String) {
        deleteUserInfoAndTokenUC.invoke(viewModelScope) { resource ->
            when (resource) {
                is Resource.Failure -> {
                    logger.debug("delete user info and token failed: ${resource.exception.message}")
                    // Handle failure if needed
                }
                is Resource.Progress -> {
                    // Handle progress if needed
                }
                is Resource.Success -> {
                    logger.debug("delete user info and token success")
                    sendEvent(ProfileContract.ProfileEvent.SignOutSuccess(message))
                }
            }
        }
    }

    override fun clearState() {
        setState(ProfileContract.ProfileViewState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when(action) {
            is ProfileContract.ProfileAction.SignOut->{ logout() }
            is ProfileContract.ProfileAction.EditProfile->{ sendEvent(ProfileContract.ProfileEvent.EditProfileNavigation) }
        }
    }
    companion object {
        private val logger = getClassLogger()
    }
}