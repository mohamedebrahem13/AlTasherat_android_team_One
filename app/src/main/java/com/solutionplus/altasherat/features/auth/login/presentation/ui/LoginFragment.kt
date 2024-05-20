package com.solutionplus.altasherat.features.auth.login.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentLoginBinding
import com.solutionplus.altasherat.features.auth.login.data.models.request.UserLoginRequest
import com.solutionplus.altasherat.features.auth.login.data.models.request.PhoneLoginRequest
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginViewModel
import com.solutionplus.altasherat.features.auth.login.presentation.viewmodel.LoginContracts
import com.solutionplus.altasherat.features.auth.ui.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewMode by viewModels<LoginViewModel>()
    private lateinit var authActivity: AuthActivity

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        val userLoginRequest =
            UserLoginRequest("ibrahem2@gmail.com", "123456544789", PhoneLoginRequest("0020", "2266989857"))
        authActivity.triggerButton {
            loginViewMode.processIntent(LoginContracts.MainAction.Login(userLoginRequest))
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {

    }

    override fun onResume() {
        super.onResume()
        authActivity.updateButtonText(getString(R.string.login_text))

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        authActivity = context as AuthActivity
    }
}