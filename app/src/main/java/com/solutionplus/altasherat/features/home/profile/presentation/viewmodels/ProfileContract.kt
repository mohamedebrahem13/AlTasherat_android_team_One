package com.solutionplus.altasherat.features.home.profile.presentation.viewmodels

import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewEvent
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewState
import com.solutionplus.altasherat.features.services.user.domain.models.User

interface ProfileContract {

    sealed class ProfileAction : ViewAction {
        data object SignOut : ProfileAction()
        data object EditProfile : ProfileAction()
        data object ChangePassword : ProfileAction()
        data object AboutUs : ProfileAction()
        data object ContactUs : ProfileAction()
        data object TermsAndConditions : ProfileAction()
        data object Language : ProfileAction()
        data object PrivacyPolicy : ProfileAction()
    }

    sealed class ProfileEvent : ViewEvent {
        data class UserLoaded(val user: User) : ProfileEvent()
        data class SignOutSuccess(val message: String) : ProfileEvent()
        data object EditProfileNavigation : ProfileEvent()
        data object ChangePasswordNavigation : ProfileEvent()
        data object AboutUsNavigation : ProfileEvent()
        data object ContactUsNavigation : ProfileEvent()
        data object TermsAndConditionsNavigation : ProfileEvent()
        data object LanguageSelectionNavigation : ProfileEvent()
        data object PrivacyPolicyNavigation : ProfileEvent()
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