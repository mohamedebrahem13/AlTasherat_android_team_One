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
        authActivity.triggerButton {
            login()
        }
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

    }

    override fun viewInit() {

    }

    private fun login() {
        val phoneNumber = binding.phoneEt.text.toString().trim()
        val countryCode = binding.countryCodeEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val phoneRequest = PhoneLoginRequest(countryCode, phoneNumber)
        val userLoginRequest = UserLoginRequest(
            password = password,
            phoneLoginRequest = phoneRequest
        )
        loginViewMode.processIntent(LoginContracts.MainAction.Login(userLoginRequest))

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