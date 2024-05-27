package com.solutionplus.altasherat.features.home.profile.presentation.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface ProfileContract {

    sealed class ProfileAction : ViewAction {
        data object SignOut : ProfileAction()
    }

    sealed class ProfileEvent : ViewEvent {
        data class UserLoaded(val user: User) : ProfileEvent()
        data object SignOutSuccess : ProfileEvent()
    }

    data class ProfileViewState(
        val isLoading: Boolean,
        val user: User?,
        val exception: AlTasheratException?,
        val action: ViewAction?
    ) : ViewState {
        companion object {
            fun initial() = ProfileViewState(isLoading = false, user = null, exception = null, action = null)
        }
    }
}